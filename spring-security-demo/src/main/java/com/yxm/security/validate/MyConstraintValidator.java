package com.yxm.security.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * @author yexinming
 * @date 2020/2/20
 **/
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    /**
     * 注意:
     * 1.ConstraintValidator<A,T>  A-指代注解  T-指代注解A所修饰属性的类型;我们定义成Object
     * 2.我们可以把spring的bean通过Autowire注解引入到此类里面的成员变量
     * 3.在MyConstraintValidator类上不用添加@Component注解,因为实现ConstraintValidator的类会自动被spring管理
     * @param myConstraint
     */
/*    @Autowired
    private HelloService helloService;*/

    @Override
    public void initialize(MyConstraint myConstraint) {
       System.out.println("my ConstraintValidator init");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        System.out.println(o);
        return false;
    }
}
