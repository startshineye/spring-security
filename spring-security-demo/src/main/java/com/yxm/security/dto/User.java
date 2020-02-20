package com.yxm.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author yexinming
 * @date 2020/2/20
 **/
public class User {
    /**
     * 使用接口声明多个视图
     */
    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{};

    private String id;
    private String username;

    @NotBlank
    private String password;

    private Date birthday;

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }
    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }
    //在值对象的GET方法上指定视图
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
