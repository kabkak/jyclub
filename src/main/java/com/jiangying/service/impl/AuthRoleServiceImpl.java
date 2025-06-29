package com.jiangying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.AuthRoleMapper;
import com.jiangying.pojo.entity.AuthRole;
import com.jiangying.service.AuthRoleService;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements AuthRoleService {
} 