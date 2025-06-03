package com.jiangying.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(factory);
        // 设置键的序列化方式为字符串序列化
        template.setKeySerializer(new StringRedisSerializer());
        // 设置哈希键的序列化方式为字符串序列化
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置哈希值和值的序列化方式为JSON序列化
        template.setHashValueSerializer(serializer());
        template.setValueSerializer(serializer());

        return template;
    }

    /**
     * 创建并配置JSON序列化器。
     *
     * @return 配置好的Jackson2JsonRedisSerializer对象，用于将对象序列化为JSON格式。
     */
    public Jackson2JsonRedisSerializer<Object> serializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper object = new ObjectMapper();
        // 启用对未知类型的默认推断
        object.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 设置在反序列化时，对于未知的属性不抛出异常
        object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        object.registerModule(new JavaTimeModule());
        // 设置所有属性可见
        object.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 配置ObjectMapper到序列化器
        serializer.setObjectMapper(object);
        return serializer;
    }
}


