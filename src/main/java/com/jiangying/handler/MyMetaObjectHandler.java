package com.jiangying.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("isDeleted", 0, metaObject);

        try {
            if (StpUtil.isLogin()) {
                String loginId = StpUtil.getLoginIdAsString();
                this.setFieldValByName("createdBy", loginId, metaObject);
                this.setFieldValByName("updateBy", loginId, metaObject);
            }
        } catch (Exception e) {
            log.error("Failed to get login user info for insert fill", e);
            // 这里可以设置一个默认值，比如 "system"
            this.setFieldValByName("createdBy", "system", metaObject);
            this.setFieldValByName("updateBy", "system", metaObject);
        }
    }

    /**
     * 更新时填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        try {
            if (StpUtil.isLogin()) {
                String loginId = StpUtil.getLoginIdAsString();
                this.setFieldValByName("updateBy", loginId, metaObject);
            }
        } catch (Exception e) {
            log.error("Failed to get login user info for update fill", e);
            this.setFieldValByName("updateBy", "system", metaObject);
        }
    }
} 