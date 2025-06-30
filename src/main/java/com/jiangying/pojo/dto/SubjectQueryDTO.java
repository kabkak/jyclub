package com.jiangying.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubjectQueryDTO {
    private int pageNum = 1;
    private int pageSize = 10;
    private String subjectName;
    private Integer subjectType;
    private Long categoryId;
    private List<Long> labelIds;
} 