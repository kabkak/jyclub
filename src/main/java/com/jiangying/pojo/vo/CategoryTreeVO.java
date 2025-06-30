package com.jiangying.pojo.vo;

import com.jiangying.pojo.entity.SubjectCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeVO implements Serializable {
    private Long id;
    private String categoryName;
    private Long parentId;
    private List<CategoryTreeVO> children;
} 