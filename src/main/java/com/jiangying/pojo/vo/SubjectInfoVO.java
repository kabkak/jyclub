package com.jiangying.pojo.vo;

import com.jiangying.pojo.entity.SubjectInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubjectInfoVO extends SubjectInfo {

    private List<String> categoryName;

    private List<String> labelName;
} 