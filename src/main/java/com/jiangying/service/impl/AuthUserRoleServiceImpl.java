package com.jiangying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.AuthUserRoleMapper;
import com.jiangying.pojo.entity.AuthUserRole;
import com.jiangying.service.AuthUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class AuthUserRoleServiceImpl extends ServiceImpl<AuthUserRoleMapper, AuthUserRole> implements AuthUserRoleService {
} 