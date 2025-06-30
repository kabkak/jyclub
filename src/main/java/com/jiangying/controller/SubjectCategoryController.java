package com.jiangying.controller;

import com.jiangying.pojo.result.Result;
import com.jiangying.pojo.vo.CategoryTreeVO;
import com.jiangying.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("subjectCategoryController")
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @GetMapping("/tree")
    public Result<List<CategoryTreeVO>> getCategoryTree() {
        List<CategoryTreeVO> categoryTree = subjectCategoryService.getCategoryTree();
        return Result.success(categoryTree);
    }
} 