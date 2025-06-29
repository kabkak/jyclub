package com.jiangying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangying.pojo.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {
} 