package com.jiangying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangying.pojo.entity.AuthPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthPermissionMapper extends BaseMapper<AuthPermission> {
    List<String> selectPermissionKeyByUserId(Long id);
} 