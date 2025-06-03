package com.jiangying.controller;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.jiangying.constant.KeyConstant;
import com.jiangying.utils.ContentUtil;
import com.jiangying.utils.MessageUtil;
import com.jiangying.utils.SHA1;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class CallBackController {

    private static final String token = "kab";

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

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
        String shaStr = SHA1.getSHA1(token, timestamp, nonce, "");
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
        String msg = null;
        if (msgType.equals("text")) {
            String content = messageMap.get("Content");
            log.info("接收到微信消息：fromUserName：{}，content：{}", fromUserName, content);
            if (content.equals("芝麻开门")) {
                byte[] c = RandomUtil.randomBytes(10);
                String key = new String(c);
                redisTemplate.opsForValue().get(key);
                //如果有则返回请重新输出
                if (ObjectUtil.isNotEmpty(redisTemplate.opsForValue().get(key))) {
                    return ContentUtil.getContent(fromUserName, toUserName, "发送了未知错误");
                }

//                // 解密
//                String decrypt = SecureUtil.aes(KeyConstant.AES_KEY.getBytes())
//                        .decryptStr(encrypt, CharsetUtil.CHARSET_UTF_8);

                redisTemplate.opsForValue().set(key, fromUserName, 30, java.util.concurrent.TimeUnit.SECONDS);
                //获得6位随机验证码
                msg = "叮咚～ 您的密钥是： " + key;
                //保存到redis todo
            } else {
                msg = "哎呀，你忘记暗号啦～";
            }
        } else if (msgType.equals("event")) {
            String event = messageMap.get("Event");
            if (event.equals("subscribe")) {
                // 添加用户信息 todo
            } else {
                //删除用户信息 todo
            }

        }

        return ContentUtil.getContent(fromUserName, toUserName, msg);
    }


}