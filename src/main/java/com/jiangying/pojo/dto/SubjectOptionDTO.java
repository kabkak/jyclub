package com.jiangying.pojo.dto;

import lombok.Data;

@Data
public class SubjectOptionDTO {

    /**
     * 选项类型 (A,B,C,D)
     */
    private Integer optionType;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 是否正确
     */
    private Integer isCorrect;

} 