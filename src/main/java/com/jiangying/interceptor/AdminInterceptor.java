package com.jiangying.interceptor;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jiangying.constant.KeyConstant;
import com.jiangying.pojo.result.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截请求: {}", request.getRequestURI());
        // 示例：检查请求头中的 token
        String token = request.getHeader("Authorization");
        log.info("请求携带的 token: {}", token);
        if (StrUtil.isEmpty(token)) {
            log.warn("请求未携带 token，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;
        }
        // 解密
        String decrypt = null;
        try {
            decrypt = SecureUtil.aes(KeyConstant.AES_KEY.getBytes())
                    .decryptStr(token, CharsetUtil.CHARSET_UTF_8);
            log.info("解密后的值: {}", decrypt); // 增加日志输出
        } catch (Exception e) {
            log.error("解密失败，异常信息: {}", e.getMessage(), e); // 增加堆栈信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;
        }
        // 显式检查 decrypt 是否为 null 或空字符串
        if (decrypt == null || StrUtil.isBlank(decrypt)) {
            log.warn("解密结果为空，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;
        }
        String string = decrypt.split("_")[0];
        String id;
        try {
             id = Objects.requireNonNull(redisTemplate.opsForValue().get(string)).toString();
        } catch (Exception e) {
            log.warn("用户不存在，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;
        }
        BaseContext.setCurrentId(Long.valueOf(id));

        log.info("请求通过拦截器验证");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求完成: {}", request.getRequestURI());
        BaseContext.removeCurrentId();
    }
}