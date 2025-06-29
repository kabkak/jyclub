package com.jiangying.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginVO {

    private Long userId;
    private String userName;
    private String nickName;
    private String avatar;
    private String token;

} 