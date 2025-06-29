package com.jiangying.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiangying.pojo.dto.AdminLoginDTO;
import com.jiangying.pojo.dto.LoginDTO;
import com.jiangying.pojo.entity.AuthUser;
import com.jiangying.pojo.result.Result;
import com.jiangying.pojo.vo.LoginVO;
import com.jiangying.service.AuthUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AuthUserService authUserService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        String verificationCode = loginDTO.getVerificationCode();
        log.info("用户尝试登录，验证码: {}", verificationCode);

        if (StrUtil.isBlank(verificationCode)) {
            return Result.error("验证码不能为空");
        }

        // 1. 从Redis获取OpenID
        String openId = stringRedisTemplate.opsForValue().get(verificationCode);
        if (StrUtil.isBlank(openId)) {
            return Result.error("验证码无效或已过期");
        }

        // 2. 验证后立即删除，防止重复使用
        stringRedisTemplate.delete(verificationCode);

        // 3. 根据OpenID查询用户
        AuthUser authUser = authUserService.getOne(
                new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUserName, openId)
        );

        if (ObjectUtil.isNull(authUser)) {
            log.warn("登录失败，未找到用户: {}", openId);
            return Result.error("用户不存在，请先关注公众号");
        }

        // 4. 检查用户状态
        if (authUser.getStatus() != 0 || authUser.getIsDeleted() != 0) {
            log.warn("登录失败，用户状态异常: {}, status: {}, isDeleted: {}", openId, authUser.getStatus(), authUser.getIsDeleted());
            return Result.error("账户已被禁用或注销");
        }

        // 5. 使用Sa-Token登录
        StpUtil.login(authUser.getId());
        String token = StpUtil.getTokenValue();
        log.info("用户 {} 登录成功, 生成的Token为: {}", authUser.getNickName(), token);

        // 6. 构造VO并返回
        LoginVO loginVO = new LoginVO()
                .setUserId(authUser.getId())
                .setUserName(authUser.getUserName())
                .setNickName(authUser.getNickName())
                .setAvatar(authUser.getAvatar())
                .setToken(token);

        return Result.success(loginVO);
    }

    @PostMapping("/login/admin")
    public Result<LoginVO> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        log.info("管理员尝试登录: {}", username);

        if (StrUtil.hasBlank(username, password)) {
            return Result.error("用户名或密码不能为空");
        }

        // 1. 查询用户
        AuthUser authUser = authUserService.getOne(
                new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUserName, username)
        );

        if (ObjectUtil.isNull(authUser)) {
            return Result.error("用户名或密码错误");
        }
        
        // 2. 校验密码
        String encryptedPassword = SaSecureUtil.md5(password);
        if (!encryptedPassword.equals(authUser.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 3. 检查用户状态
        if (authUser.getStatus() != 0 || authUser.getIsDeleted() != 0) {
            return Result.error("账户已被禁用或注销");
        }
        
        // 4. 登录并校验角色
        StpUtil.login(authUser.getId());
        try {
            StpUtil.checkRole("admin");
        } catch (Exception e) {
            StpUtil.logout(); // 权限不足，登出
            return Result.error("无权访问，仅管理员可登录");
        }

        String token = StpUtil.getTokenValue();
        log.info("管理员 {} 登录成功, Token: {}", username, token);

        // 5. 构造VO返回
        LoginVO loginVO = new LoginVO()
                .setUserId(authUser.getId())
                .setUserName(authUser.getUserName())
                .setNickName(authUser.getNickName())
                .setAvatar(authUser.getAvatar())
                .setToken(token);

        return Result.success(loginVO);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        if (StpUtil.isLogin()) {
            log.info("用户 {} 退出登录", StpUtil.getLoginId());
            StpUtil.logout();
        }
        return Result.success("退出成功");
    }
} 