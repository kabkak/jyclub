package com.jiangying.service.handler;

import com.jiangying.enums.SubjectInfoTypeEnum;
import com.jiangying.pojo.dto.SubjectInfoDTO;
import com.jiangying.pojo.dto.SubjectOptionDTO;
import com.jiangying.pojo.entity.SubjectJudge;
import com.jiangying.service.SubjectJudgeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class JudgeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectJudgeService subjectJudgeService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    @Override
    public void add(SubjectInfoDTO subjectInfoDTO) {
        Long subjectId = subjectInfoDTO.getSubjectInfo().getId();
        SubjectOptionDTO judgeOption = subjectInfoDTO.getOptionList().get(0);
        SubjectJudge judge = new SubjectJudge();
        judge.setSubjectId(subjectId);
        judge.setIsCorrect(judgeOption.getIsCorrect());
        subjectJudgeService.save(judge);
    }
} 