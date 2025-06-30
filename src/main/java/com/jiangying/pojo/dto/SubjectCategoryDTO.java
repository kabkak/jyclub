package com.jiangying.pojo.dto;

import lombok.Data;

@Data
public class SubjectCategoryDTO {

    private String categoryName;

    private Integer categoryType;

    private String imageUrl;

    private Long parentId;
}
