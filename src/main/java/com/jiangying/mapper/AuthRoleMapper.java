package com.jiangying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangying.pojo.entity.AuthRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthRoleMapper extends BaseMapper<AuthRole> {

    List<String> selectRoleKeyByUserId(Long id);
} 