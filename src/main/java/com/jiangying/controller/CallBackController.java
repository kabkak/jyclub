package com.jiangying.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiangying.pojo.entity.AuthUser;
import com.jiangying.service.AuthUserService;
import com.jiangying.utils.ContentUtil;
import com.jiangying.utils.MessageUtil;
import com.jiangying.utils.SHA1;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class CallBackController {

    private static final String WECHAT_TOKEN = "kab";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AuthUserService authUserService;

    /**
     * 回调消息校验
     */
    @GetMapping("callback")
    public String callback(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        log.info("get验签请求参数：signature:{}，timestamp:{}，nonce:{}，echostr:{}",
                signature, timestamp, nonce, echostr);
        String shaStr = SHA1.getSHA1(WECHAT_TOKEN, timestamp, nonce, "");
        if (signature.equals(shaStr)) {
            return echostr;
        }
        return "unknown";
    }

    @PostMapping(value = "callback", produces = "application/xml;charset=UTF-8")
    public String callback(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {
        log.info("接收到微信消息：requestBody：{}", requestBody);
        Map<String, String> messageMap = MessageUtil.parseXml(requestBody);

        String fromUserName = messageMap.get("FromUserName");
        String toUserName = messageMap.get("ToUserName");
        String msgType = messageMap.get("MsgType");
        String msg = "系统繁忙，请稍后再试"; // Default message

        try {
            if ("text".equals(msgType)) {
                String content = messageMap.get("Content");
                log.info("接收到微信文本消息：fromUserName：{}，content：{}", fromUserName, content);
                if ("芝麻开门".equals(content)) {
                    String key = RandomUtil.randomString(6);
                    // 使用 StringRedisTemplate 并设置合理的过期时间
                    stringRedisTemplate.opsForValue().set(key, fromUserName, 5, TimeUnit.MINUTES);
                    msg = "叮咚～ 您的登录密钥是：" + key + "，5分钟内有效。";
                } else {
                    msg = "哎呀，你忘记暗号啦～";
                }
            } else if ("event".equals(msgType)) {
                String event = messageMap.get("Event");
                log.info("接收到微信事件：fromUserName：{}，event：{}", fromUserName, event);
                if ("subscribe".equals(event)) {
                    handleSubscription(fromUserName);
                    msg = "欢迎关注！输入 芝麻开门 获取登录密钥，开启您的学习之旅吧！";
                } else if ("unsubscribe".equals(event)) {
                    handleUnsubscription(fromUserName);
                    log.info("用户 {} 已取消关注", fromUserName);
                    // 微信要求取消订阅时返回空字符串或success
                    return "success";
                }
            }
        } catch (Exception e) {
            log.error("处理微信回调时发生错误", e);
        }

        return ContentUtil.getContent(fromUserName, toUserName, msg);
    }

    private void handleSubscription(String openId) {
        AuthUser authUser = authUserService.getOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUserName, openId));
        if (ObjectUtil.isNotNull(authUser)) {
            // 用户重新关注，更新状态
            authUser.setStatus(0); // 0-启用
            authUser.setUpdateTime(LocalDateTime.now());
            authUserService.updateById(authUser);
            log.info("用户 {} 重新关注", openId);
        } else {
            // 新用户关注
            AuthUser newUser = new AuthUser()
                    .setUserName(openId) // 使用 OpenID 作为唯一用户名
                    .setNickName("电科小子_" + RandomUtil.randomString(6))
                    .setAvatar("https://tse3-mm.cn.bing.net/th/id/OIP-C.7GLMYPqMlt2LgkbPsOnDIAAAAA?rs=1&pid=ImgDetMain")
                    .setStatus(0) // 0-启用
                    .setIsDeleted(0)
                    .setCreatedTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            authUserService.save(newUser);
            log.info("新用户 {} 关注成功", openId);
        }
    }

    private void handleUnsubscription(String openId) {
        AuthUser authUser = authUserService.getOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUserName, openId));
        if (ObjectUtil.isNotNull(authUser)) {
            authUser.setStatus(1); // 1-禁用
            authUser.setUpdateTime(LocalDateTime.now());
            authUserService.updateById(authUser);
        }
    }
}