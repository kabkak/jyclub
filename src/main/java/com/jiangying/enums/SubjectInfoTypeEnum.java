package com.jiangying.enums;

import lombok.Getter;

@Getter
public enum SubjectInfoTypeEnum {
    RADIO(1, "单选"),
    MULTIPLE(2, "多选"),
    JUDGE(3, "判断"),
    BRIEF(4, "简答");

    private final int code;
    private final String desc;

    SubjectInfoTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubjectInfoTypeEnum getByCode(int code) {
        for (SubjectInfoTypeEnum value : SubjectInfoTypeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
} 