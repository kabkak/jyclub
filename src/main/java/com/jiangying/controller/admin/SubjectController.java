package com.jiangying.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.result.PageResult;
import com.jiangying.pojo.result.Result;
import com.jiangying.pojo.vo.SubjectInfoVO;
import com.jiangying.service.SubjectInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/subject")
@Slf4j
@SaCheckRole("admin")
public class SubjectController {

    @Resource
    private SubjectInfoService subjectInfoService;

    @PutMapping("/update")
    public Result<Void> update(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        log.info("修改题目: {}", subjectInfoDTO.getSubjectInfo().getSubjectName());
        subjectInfoService.update(subjectInfoDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除题目: id={}", id);
        subjectInfoService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        log.info("新增题目: {}", subjectInfoDTO.getSubjectInfo().getSubjectName());
        subjectInfoService.add(subjectInfoDTO);
        return Result.success();
    }
}