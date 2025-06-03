package com.jiangying.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.jiangying.constant.KeyConstant;
import com.jiangying.pojo.result.Result;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody String verificationCode) {

        Map<String, Object> jsonMap = JSONUtil.parseObj(verificationCode);
        String code = String.valueOf(jsonMap.get("verificationCode"));
        //从redis进行获得token
        String fromUserName = String.valueOf(redisTemplate.opsForValue().get(code)
        );
        if (fromUserName == null) {
            return Result.error("验证码已过期");
        }
        // 生成 token
        String encrypt = SecureUtil.aes(KeyConstant.AES_KEY.getBytes())
                .encryptBase64(fromUserName, CharsetUtil.CHARSET_UTF_8);
        // 返回包含 token 的成功结果
        return Result.success(Map.of("t-o-k-e-n", encrypt));
    }


}
