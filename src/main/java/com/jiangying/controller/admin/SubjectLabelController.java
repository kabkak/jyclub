package com.jiangying.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.jiangying.pojo.dto.SubjectLabelDTO;
import com.jiangying.pojo.entity.SubjectLabel;
import com.jiangying.pojo.result.Result;
import com.jiangying.service.SubjectLabelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/subject/label")
@Slf4j
@SaCheckRole("admin")
public class SubjectLabelController {

    @Resource
    private SubjectLabelService subjectLabelService;

    @PostMapping("/add")
    public Result<Void> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("新增题目标签: {}", subjectLabelDTO.getLabelName());
        subjectLabelService.add(subjectLabelDTO);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("修改题目标签: {}", subjectLabelDTO.getLabelName());
        subjectLabelService.update(subjectLabelDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除题目标签: id={}", id);
        subjectLabelService.deleteById(id);
        return Result.success();
    }
    
    @GetMapping("/list")
    public Result<List<SubjectLabel>> list(SubjectLabelDTO subjectLabelDTO) {
        log.info("查询题目标签列表: {}", subjectLabelDTO);
        List<SubjectLabel> list = subjectLabelService.list(subjectLabelDTO);
        return Result.success(list);
    }
} 