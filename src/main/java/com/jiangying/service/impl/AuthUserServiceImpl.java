package com.jiangying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.AuthUserMapper;
import com.jiangying.pojo.entity.AuthUser;
import com.jiangying.service.AuthUserService;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {
} 