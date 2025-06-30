package com.jiangying.pojo.vo;

import com.jiangying.pojo.dto.SubjectOptionDTO;
import com.jiangying.pojo.entity.SubjectInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubjectDetailVO extends SubjectInfo {
    private List<SubjectOptionDTO> optionList;
} 
 