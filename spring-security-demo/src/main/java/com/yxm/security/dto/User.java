package com.yxm.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.yxm.security.validate.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
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

    @MyConstraint(message = "这是一个测试")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;//声明生日是过去时间

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
