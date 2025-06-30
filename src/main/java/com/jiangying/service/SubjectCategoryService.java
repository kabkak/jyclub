package com.jiangying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangying.pojo.dto.SubjectCategoryDTO;
import com.jiangying.pojo.entity.SubjectCategory;
import com.jiangying.pojo.vo.CategoryTreeVO;

import java.util.List;

public interface SubjectCategoryService extends IService<SubjectCategory> {
    void add(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategory> queryPrimaryCategory();

    void deleteById(Long id);

    List<CategoryTreeVO> getCategoryTree();
}
