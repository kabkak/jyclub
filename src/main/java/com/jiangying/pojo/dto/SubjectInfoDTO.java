package com.jiangying.pojo.dto;

import com.jiangying.pojo.entity.SubjectInfo;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfoDTO {

    /**
     * 题目信息
     */
    private SubjectInfo subjectInfo;

    /**
     * 题目答案
     */
    private List<SubjectOptionDTO> optionList;

    /**
     * 题目分类
     */
    private List<String> categoryIds;

    /**
     * 题目标签
     */
    private List<String> labelIds;
} 