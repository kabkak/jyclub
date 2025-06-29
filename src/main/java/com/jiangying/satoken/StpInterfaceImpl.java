package com.jiangying.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.jiangying.mapper.AuthPermissionMapper;
import com.jiangying.mapper.AuthRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sa-Token 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private AuthRoleMapper authRoleMapper;

    @Resource
    private AuthPermissionMapper authPermissionMapper;


    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return authPermissionMapper.selectPermissionKeyByUserId(Long.valueOf(String.valueOf(loginId)));
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return authRoleMapper.selectRoleKeyByUserId(Long.valueOf(String.valueOf(loginId)));
    }
} 