package com.yxm.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yxm.security.dto.User;
import com.yxm.security.dto.UserQueryCondition;
import com.yxm.security.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author yexinming
 * @date 2020/2/20
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println("update:" + id);
    }

    /**
     * 接口日期常见处理:
     * 1.通过日期格式化转换,比如: yyyy-MM-dd HH:mm:ss
     * 但是当我们多个终端:web、app、第三方同时调用这个接口时候:并且在每个终端显示的格式可能不一样
     * app端显示时分秒、web端显示年月日
     * 2.所有参数传递时候:不用指定格式时间传递,那传什么呢?传时间戳
     * 时间戳是一个精确到毫秒的值,前端拿到时间戳之后决定怎样展示。
     * @param user
     * @return
     */

    @PostMapping
    public User create(@Valid @RequestBody User user){
        /**
         * 参数校验:最常用方式:
         * 1.自己写代码校验:自己写 非常繁琐 可能 有时候有代码重复修改
         * 2.java发展到现在,其实常见的都有现有框架组件去解决的:在对象属性上添加注解:@Valid会根据参数对象属性注解进行校验;
         *
         *上面注解添加@Valid在我们进行参数校验时候,如果没有过的话直接打回来返回4xx错误码(Restful API就是按照code);有时候参数没有校验通过的时候,我们也是需要进入方法体做一些处理的
         * 此时用到注解:BingingResult
         *
         * BingingResult类需要跟@Valid配合的
         */
        /*if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error->System.out.println(error.getDefaultMessage()));
            //may not be empty
        }*/

        //使用反射工具
        System.out.println("create:"+ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        //create:com.yxm.security.dto.User@6ffdbeef[
        //  id=<null>
        //  username=Jack
        //  password=<null>
        //  birthday=Thu Feb 20 17:21:57 CST 2020
        //]

        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors){
        if(errors.hasErrors()){
            //error强制转换并打印出
            errors.getAllErrors().stream().forEach(error->{
                   /* FieldError fieldError = (FieldError)error;
                    String message = fieldError.getField()+" "+error.getDefaultMessage();*/
                    System.out.println(error.getDefaultMessage());
            });
        }
        //使用反射工具
        System.out.println("update:"+ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        user.setId("1");
        return user;
    }


    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public  List<User> user(UserQueryCondition condition, @PageableDefault(page = 1,size = 10,sort = "username,asc") Pageable pageable){
        //使用反射工具
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        User user = new User();
        List<User> users = Arrays.asList(user, user, user);
        return  users;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation(value = "查询详情服务")
    public User DetailInfo(@ApiParam("用户id") @PathVariable(name = "id") String xxx){
        //throw new UserNotExistException();
        User user = new User();
        user.setUsername("Jack");
        System.out.println("====DetailInfo=====");
        return user;
    }
}
