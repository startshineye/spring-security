package com.yxm.security.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yexinming
 * @date 2020/2/20
 **/
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)//定义当前注解用什么类去校验;把校验的逻辑写到某个类里
public @interface MyConstraint {
    /**
     * 参考Hibernate Validator相关注解我们知道 校验类的相关注解需要添加基本的三个属性
     */
    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
