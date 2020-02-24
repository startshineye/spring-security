package com.yxm.security.core.validate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author yexinming
 * @date 2020/2/24
 **/
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
