package com.jiangying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.AuthPermissionMapper;
import com.jiangying.pojo.entity.AuthPermission;
import com.jiangying.service.AuthPermissionService;
import org.springframework.stereotype.Service;

@Service
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermission> implements AuthPermissionService {
} 