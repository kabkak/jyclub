package com.jiangying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.SubjectLabelMapper;
import com.jiangying.mapper.SubjectMappingMapper;
import com.jiangying.pojo.dto.SubjectLabelDTO;
import com.jiangying.pojo.entity.SubjectLabel;
import com.jiangying.pojo.entity.SubjectMapping;
import com.jiangying.service.SubjectLabelService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectLabelServiceImpl extends ServiceImpl<SubjectLabelMapper, SubjectLabel> implements SubjectLabelService {

    @Resource
    private SubjectMappingMapper subjectMappingMapper;

    @Override
    public void add(SubjectLabelDTO subjectLabelDTO) {
        SubjectLabel subjectLabel = new SubjectLabel();
        BeanUtils.copyProperties(subjectLabelDTO, subjectLabel);
        baseMapper.insert(subjectLabel);
    }

    @Override
    public void update(SubjectLabelDTO subjectLabelDTO) {
        SubjectLabel subjectLabel = new SubjectLabel();
        BeanUtils.copyProperties(subjectLabelDTO, subjectLabel);
        baseMapper.updateById(subjectLabel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // 1. 删除标签本身
        baseMapper.deleteById(id);
        // 2. 删除标签与题目的关联关系
        LambdaQueryWrapper<SubjectMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectMapping::getLabelId, id);
        subjectMappingMapper.delete(queryWrapper);
    }

    @Override
    public List<SubjectLabel> list(SubjectLabelDTO subjectLabelDTO) {
        LambdaQueryWrapper<SubjectLabel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(subjectLabelDTO.getId() != null, SubjectLabel::getId, subjectLabelDTO.getId());
        queryWrapper.like(StringUtils.hasText(subjectLabelDTO.getLabelName()), SubjectLabel::getLabelName, subjectLabelDTO.getLabelName());
        queryWrapper.eq(StringUtils.hasText(subjectLabelDTO.getCategoryId()), SubjectLabel::getCategoryId, subjectLabelDTO.getCategoryId());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SubjectLabel> getLabelsByCategoryId(Long categoryId) {
        // 1. 根据 categoryId 找到所有不重复的 labelId
        LambdaQueryWrapper<SubjectMapping> mappingQueryWrapper = new LambdaQueryWrapper<>();
        mappingQueryWrapper.eq(SubjectMapping::getCategoryId, categoryId)
                .select(SubjectMapping::getLabelId);
        List<SubjectMapping> mappings = subjectMappingMapper.selectList(mappingQueryWrapper);
        if (CollectionUtils.isEmpty(mappings)) {
            return Collections.emptyList();
        }
        List<Long> labelIds = mappings.stream().map(SubjectMapping::getLabelId).distinct().collect(Collectors.toList());

        // 2. 根据 labelIds 找到所有 label
        return baseMapper.selectBatchIds(labelIds);
    }
} 