package com.jiangying.pojo.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个人介绍
     */
    private String introduce;

} 