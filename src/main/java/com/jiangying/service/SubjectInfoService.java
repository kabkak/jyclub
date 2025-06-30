package com.jiangying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.dto.SubjectQueryDTO;
import com.jiangying.pojo.entity.SubjectInfo;
import com.jiangying.pojo.result.PageResult;
import com.jiangying.pojo.vo.SubjectInfoVO;

public interface SubjectInfoService extends IService<SubjectInfo> {
    void add(SubjectInfoDTO subjectInfoDTO);

    void update(SubjectInfoDTO subjectInfoDTO);

    void deleteById(Long id);

    PageResult<SubjectInfoVO> pageQuery(SubjectQueryDTO subjectQueryDTO);
}