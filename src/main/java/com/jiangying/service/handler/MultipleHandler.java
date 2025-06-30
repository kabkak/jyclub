package com.jiangying.service.handler;

import com.jiangying.enums.SubjectInfoTypeEnum;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.entity.SubjectMultiple;
import com.jiangying.service.SubjectMultipleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class MultipleHandler implements SubjectTypeHandler {

    @Resource
    private SubjectMultipleService subjectMultipleService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoDTO subjectInfoDTO) {
        Long subjectId = subjectInfoDTO.getSubjectInfo().getId();
        subjectInfoDTO.getOptionList().forEach(option -> {
            SubjectMultiple multiple = new SubjectMultiple();
            multiple.setSubjectId(subjectId);
            multiple.setOptionType(Long.valueOf(option.getOptionType()));
            multiple.setOptionContent(option.getOptionContent());
            multiple.setIsCorrect(option.getIsCorrect());
            subjectMultipleService.save(multiple);
        });
    }
} 