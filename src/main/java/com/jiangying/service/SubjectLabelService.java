package com.jiangying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangying.pojo.dto.SubjectLabelDTO;
import com.jiangying.pojo.entity.SubjectLabel;

import java.util.List;

public interface SubjectLabelService extends IService<SubjectLabel> {
    void add(SubjectLabelDTO subjectLabelDTO);

    void update(SubjectLabelDTO subjectLabelDTO);

    void deleteById(Long id);

    List<SubjectLabel> list(SubjectLabelDTO subjectLabelDTO);

    List<SubjectLabel> getLabelsByCategoryId(Long categoryId);
} 