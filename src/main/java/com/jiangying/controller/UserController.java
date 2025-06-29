package com.jiangying.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.jiangying.pojo.dto.UserUpdateDTO;
import com.jiangying.pojo.entity.AuthUser;
import com.jiangying.pojo.result.Result;
import com.jiangying.service.AuthUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/user")
@SaCheckLogin // 整个控制器下的所有接口都需要登录才能访问
public class UserController {

    @Resource
    private AuthUserService authUserService;

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<AuthUser> getUserInfo() {
        long userId = StpUtil.getLoginIdAsLong();
        log.info("获取用户 {} 的信息", userId);
        AuthUser user = authUserService.getById(userId);
        // 敏感信息脱敏
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    /**
     * 修改当前登录用户信息
     */
    @PutMapping("/info")
    public Result<Void> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        long userId = StpUtil.getLoginIdAsLong();
        log.info("用户 {} 尝试更新信息", userId);

        AuthUser userToUpdate = BeanUtil.copyProperties(userUpdateDTO, AuthUser.class);
        userToUpdate.setId(userId);
        userToUpdate.setUpdateTime(LocalDateTime.now());

        authUserService.updateById(userToUpdate);
        log.info("用户 {} 信息更新成功", userId);

        return Result.success();
    }
} 