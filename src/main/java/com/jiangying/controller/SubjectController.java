package com.jiangying.controller;

import com.jiangying.pojo.result.PageResult;
import com.jiangying.pojo.result.Result;
import com.jiangying.pojo.vo.SubjectInfoDTO;
import com.jiangying.pojo.vo.SubjectInfoVO;
import com.jiangying.pojo.vo.SubjectQueryDTO;
import com.jiangying.service.SubjectInfoService;
import com.jiangying.service.SubjectLabelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("subjectController")
@RequestMapping("/subject")
@Slf4j
public class SubjectController {

    @Resource
    private SubjectInfoService subjectInfoService;
    
    @Resource
    private SubjectLabelService subjectLabelService;

    @GetMapping("/getLabelsByCategoryId/{categoryId}")
    public Result<List<SubjectLabel>> getLabelsByCategoryId(@PathVariable Long categoryId) {
        log.info("根据分类ID查询标签: categoryId={}", categoryId);
        List<SubjectLabel> labels = subjectLabelService.getLabelsByCategoryId(categoryId);
        return Result.success(labels);
    }

    @GetMapping("/list")
    public Result<PageResult<SubjectInfoVO>> list(SubjectQueryDTO subjectQueryDTO) {
        log.info("分页查询题目列表: {}", subjectQueryDTO);
        PageResult<SubjectInfoVO> pageResult = subjectInfoService.pageQuery(subjectQueryDTO);
        return Result.success(pageResult);
    }
} 