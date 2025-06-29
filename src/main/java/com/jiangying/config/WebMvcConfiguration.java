package com.jiangying.config;


import com.jiangying.interceptor.AdminInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Resource
    private AdminInterceptor adminInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/callback");
    }

    private static final long MAX_AGE = 24 * 60 * 60;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(MAX_AGE);
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

//    @Override
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //创建一个消息转换器
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        //为消息转换器 设置一个对象转换器, 对象转换器 将Java对象转换为json数据
//        converter.setObjectMapper(new JacksonObjectMapper());
//        //将消息转换器放入容器
//        converters.add(0, converter);
//    }
}
