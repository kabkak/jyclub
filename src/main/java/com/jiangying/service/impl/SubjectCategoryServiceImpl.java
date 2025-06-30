package com.jiangying.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.SubjectCategoryMapper;
import com.jiangying.pojo.dto.SubjectCategoryDTO;
import com.jiangying.pojo.entity.SubjectCategory;
import com.jiangying.pojo.vo.CategoryTreeVO;
import com.jiangying.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class SubjectCategoryServiceImpl extends ServiceImpl<SubjectCategoryMapper, SubjectCategory> implements SubjectCategoryService {

    @Resource(name = "categoryExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void add(SubjectCategoryDTO subjectCategoryDTO) {
        SubjectCategory subjectCategory = BeanUtil.copyProperties(subjectCategoryDTO, SubjectCategory.class);
        baseMapper.insert(subjectCategory);
    }

    @Override
    public List<SubjectCategory> queryPrimaryCategory() {
        LambdaQueryWrapper<SubjectCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectCategory::getParentId, 0);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public List<CategoryTreeVO> getCategoryTree() {
        // 1. 查询所有一级分类
        LambdaQueryWrapper<SubjectCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectCategory::getParentId, 0);
        List<SubjectCategory> parentCategories = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(parentCategories)) {
            return Collections.emptyList();
        }

        // 2. 并发查询二级分类
        List<CompletableFuture<CategoryTreeVO>> futures = parentCategories.stream().map(parent ->
                CompletableFuture.supplyAsync(() -> {
                    CategoryTreeVO parentVO = new CategoryTreeVO();
                    BeanUtils.copyProperties(parent, parentVO);

                    LambdaQueryWrapper<SubjectCategory> childWrapper = new LambdaQueryWrapper<>();
                    childWrapper.eq(SubjectCategory::getParentId, parent.getId());
                    List<SubjectCategory> childCategories = baseMapper.selectList(childWrapper);

                    if (!CollectionUtils.isEmpty(childCategories)) {
                        List<CategoryTreeVO> childVOs = childCategories.stream().map(child -> {
                            CategoryTreeVO childVO = new CategoryTreeVO();
                            BeanUtils.copyProperties(child, childVO);
                            return childVO;
                        }).collect(Collectors.toList());
                        parentVO.setChildren(childVOs);
                    }
                    return parentVO;
                }, threadPoolExecutor)
        ).collect(Collectors.toList());

        // 3. 组合结果
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
} 