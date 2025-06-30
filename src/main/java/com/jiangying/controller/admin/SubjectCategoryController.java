package com.jiangying.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.jiangying.pojo.dto.SubjectCategoryDTO;
import com.jiangying.pojo.entity.SubjectCategory;
import com.jiangying.pojo.result.Result;
import com.jiangying.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/subject/category")
@Slf4j
@SaCheckRole("admin")
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @PostMapping("/add")
    public Result<Void> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        log.info("新增题目分类: {}", subjectCategoryDTO.getCategoryName());
        subjectCategoryService.add(subjectCategoryDTO);
        return Result.success();
    }

    @GetMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategory>> queryPrimaryCategory() {
        return Result.success(subjectCategoryService.queryPrimaryCategory());
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody SubjectCategory subjectCategory) {
        log.info("更新题目分类: {}", subjectCategory.getCategoryName());
        subjectCategoryService.updateById(subjectCategory);
        return Result.success();
    }
    
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除题目分类: {}", id);
        subjectCategoryService.deleteById(id);
        return Result.success();
    }
} 