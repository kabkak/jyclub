package com.jiangying.service.handler;

import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.enums.SubjectInfoTypeEnum;
import com.jiangying.pojo.entity.SubjectInfo;

/**
 * 题目类型处理器
 */
public interface SubjectTypeHandler {

    /**
     * 枚举类型
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 添加题目
     */
    void add(SubjectInfoDTO subjectInfoDTO);

} 