package com.jiangying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangying.mapper.*;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.entity.*;
import com.jiangying.pojo.result.PageResult;
import com.jiangying.pojo.vo.SubjectInfoVO;
import com.jiangying.service.SubjectInfoService;
import com.jiangying.service.handler.SubjectTypeFactory;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubjectInfoServiceImpl extends ServiceImpl<SubjectInfoMapper, SubjectInfo> implements SubjectInfoService {

    @Resource
    private SubjectTypeFactory subjectTypeFactory;
    @Resource
    private SubjectMappingMapper subjectMappingMapper;
    @Resource
    private SubjectRadioMapper subjectRadioMapper;
    @Resource
    private SubjectMultipleMapper subjectMultipleMapper;
    @Resource
    private SubjectJudgeMapper subjectJudgeMapper;
    @Resource
    private SubjectBriefMapper subjectBriefMapper;
    @Resource
    private SubjectCategoryMapper subjectCategoryMapper;
    @Resource
    private SubjectLabelMapper subjectLabelMapper;

    @Override
    @Transactional
    public void add(SubjectInfoDTO subjectInfoDTO) {
        SubjectInfo subjectInfo = subjectInfoDTO.getSubjectInfo();
        baseMapper.insert(subjectInfo);
        subjectInfoDTO.setSubjectInfo(subjectInfo);
        subjectTypeFactory.getHandler(subjectInfo.getSubjectType()).add(subjectInfoDTO);
        addMapping(subjectInfoDTO);
    }

    @Override
    @Transactional
    public void update(SubjectInfoDTO subjectInfoDTO) {
        SubjectInfo subjectInfo = subjectInfoDTO.getSubjectInfo();
        baseMapper.updateById(subjectInfo);
        subjectInfoDTO.setSubjectInfo(subjectInfo);
        deleteOptionsAndMappings(subjectInfo.getId());
        subjectTypeFactory.getHandler(subjectInfo.getSubjectType()).add(subjectInfoDTO);
        addMapping(subjectInfoDTO);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.removeById(id); // 逻辑删除
        deleteOptionsAndMappings(id);
    }

    private void deleteOptionsAndMappings(Long subjectId) {
        subjectMappingMapper.delete(new LambdaQueryWrapper<SubjectMapping>().eq(SubjectMapping::getSubjectId, subjectId));
        subjectRadioMapper.delete(new LambdaQueryWrapper<SubjectRadio>().eq(SubjectRadio::getSubjectId, subjectId));
        subjectMultipleMapper.delete(new LambdaQueryWrapper<SubjectMultiple>().eq(SubjectMultiple::getSubjectId, subjectId));
        subjectJudgeMapper.delete(new LambdaQueryWrapper<SubjectJudge>().eq(SubjectJudge::getSubjectId, subjectId));
        subjectBriefMapper.delete(new LambdaQueryWrapper<SubjectBrief>().eq(SubjectBrief::getSubjectId, subjectId));
    }

    private void addMapping(SubjectInfoDTO subjectInfoDTO) {
        Long subjectId = subjectInfoDTO.getSubjectInfo().getId();
        List<String> categoryIds = subjectInfoDTO.getCategoryIds();
        List<String> labelIds = subjectInfoDTO.getLabelIds();
        if (CollectionUtils.isEmpty(categoryIds) || CollectionUtils.isEmpty(labelIds)) {
            return;
        }
        categoryIds.forEach(categoryId -> labelIds.forEach(labelId -> {
            SubjectMapping mapping = new SubjectMapping();
            mapping.setSubjectId(subjectId);
            mapping.setCategoryId(Long.valueOf(categoryId));
            mapping.setLabelId(Long.valueOf(labelId));
            subjectMappingMapper.insert(mapping);
        }));
    }

    @Override
    public PageResult<SubjectInfoVO> pageQuery(SubjectQueryDTO subjectQueryDTO) {
        Page<SubjectInfo> page = new Page<>(subjectQueryDTO.getPageNum(), subjectQueryDTO.getPageSize());

        // 1. 先根据 categoryId 和 labelIds 筛选出 subjectId
        List<Long> subjectIds = null;
        if (subjectQueryDTO.getCategoryId() != null || !CollectionUtils.isEmpty(subjectQueryDTO.getLabelIds())) {
            LambdaQueryWrapper<SubjectMapping> mappingQueryWrapper = new LambdaQueryWrapper<>();
            mappingQueryWrapper.eq(subjectQueryDTO.getCategoryId() != null, SubjectMapping::getCategoryId, subjectQueryDTO.getCategoryId());
            mappingQueryWrapper.in(!CollectionUtils.isEmpty(subjectQueryDTO.getLabelIds()), SubjectMapping::getLabelId, subjectQueryDTO.getLabelIds());
            List<SubjectMapping> mappings = subjectMappingMapper.selectList(mappingQueryWrapper);
            if (CollectionUtils.isEmpty(mappings)) {
                return new PageResult<>(0L, Collections.emptyList());
            }
            subjectIds = mappings.stream().map(SubjectMapping::getSubjectId).distinct().collect(Collectors.toList());
        }

        // 2. 再根据 subjectIds 和其他条件查询主表
        LambdaQueryWrapper<SubjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(subjectQueryDTO.getSubjectName()), SubjectInfo::getSubjectName, subjectQueryDTO.getSubjectName());
        queryWrapper.eq(subjectQueryDTO.getSubjectType() != null, SubjectInfo::getSubjectType, subjectQueryDTO.getSubjectType());
        if (subjectIds != null) {
            queryWrapper.in(SubjectInfo::getId, subjectIds);
        }
        queryWrapper.orderByDesc(SubjectInfo::getUpdateTime);

        Page<SubjectInfo> pageResult = baseMapper.selectPage(page, queryWrapper);
        List<SubjectInfo> records = pageResult.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new PageResult<>(pageResult.getTotal(), Collections.emptyList());
        }

        // 3. 组装VO，查询关联的分类和标签名
        List<Long> finalSubjectIds = records.stream().map(SubjectInfo::getId).collect(Collectors.toList());
        List<SubjectMapping> finalMappings = subjectMappingMapper.selectList(
                new LambdaQueryWrapper<SubjectMapping>().in(SubjectMapping::getSubjectId, finalSubjectIds));
        
        List<SubjectInfoVO> voList = records.stream().map(subjectInfo -> {
            SubjectInfoVO vo = new SubjectInfoVO();
            BeanUtils.copyProperties(subjectInfo, vo);

            if (!CollectionUtils.isEmpty(finalMappings)) {
                List<Long> currentCategoryIds = finalMappings.stream()
                        .filter(m -> m.getSubjectId().equals(subjectInfo.getId()))
                        .map(SubjectMapping::getCategoryId).distinct().collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(currentCategoryIds)){
                    List<SubjectCategory> categories = subjectCategoryMapper.selectBatchIds(currentCategoryIds);
                    vo.setCategoryName(categories.stream().map(SubjectCategory::getCategoryName).collect(Collectors.toList()));
                }

                List<Long> currentLabelIds = finalMappings.stream()
                        .filter(m -> m.getSubjectId().equals(subjectInfo.getId()))
                        .map(SubjectMapping::getLabelId).distinct().collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(currentLabelIds)){
                    List<SubjectLabel> labels = subjectLabelMapper.selectBatchIds(currentLabelIds);
                    vo.setLabelName(labels.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList()));
                }
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageResult<>(pageResult.getTotal(), voList);
    }
} 