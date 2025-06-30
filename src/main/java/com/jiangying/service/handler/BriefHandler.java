package com.jiangying.service.handler;

import com.jiangying.enums.SubjectInfoTypeEnum;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.dto.SubjectOptionDTO;
import com.jiangying.pojo.entity.SubjectBrief;
import com.jiangying.service.SubjectBriefService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BriefHandler implements SubjectTypeHandler {

    @Resource
    private SubjectBriefService subjectBriefService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoDTO subjectInfoDTO) {
        Long subjectId = subjectInfoDTO.getSubjectInfo().getId();
        SubjectOptionDTO briefOption = subjectInfoDTO.getOptionList().get(0);
        SubjectBrief brief = new SubjectBrief();
        brief.setSubjectId(subjectId.intValue());
        brief.setSubjectAnswer(briefOption.getOptionContent());
        subjectBriefService.save(brief);
    }
} 