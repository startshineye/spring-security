package com.yxm.security.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yexinming
 * @date 2020/2/20
 **/
public class UserQueryCondition {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
