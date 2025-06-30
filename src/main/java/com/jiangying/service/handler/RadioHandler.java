package com.jiangying.service.handler;

import com.jiangying.enums.SubjectInfoTypeEnum;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.entity.SubjectRadio;
import com.jiangying.service.SubjectRadioService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RadioHandler implements SubjectTypeHandler {

    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoDTO subjectInfoDTO) {
        Long subjectId = subjectInfoDTO.getSubjectInfo().getId();
        subjectInfoDTO.getOptionList().forEach(option -> {
            SubjectRadio radio = new SubjectRadio();
            radio.setSubjectId(subjectId);
            radio.setOptionType(option.getOptionType());
            radio.setOptionContent(option.getOptionContent());
            radio.setIsCorrect(option.getIsCorrect());
            subjectRadioService.save(radio);
        });
    }
} 