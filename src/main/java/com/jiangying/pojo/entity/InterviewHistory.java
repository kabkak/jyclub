package com.jiangying.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@TableName("interview_history")
public class InterviewHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Double avgScore;
    private String keyWords;
    private String tip;
    private String interviewUrl;
    private String createdBy;
    private Timestamp createdTime;
    private String updateBy;
    private Timestamp updateTime;
    private Integer isDeleted;
} 