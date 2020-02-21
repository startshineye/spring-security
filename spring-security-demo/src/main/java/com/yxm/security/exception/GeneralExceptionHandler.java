package com.yxm.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserNotExistException(UserNotExistException ex){
        Map<String, Object> result = new HashMap<>();
        result.put("id",ex.getId());
        result.put("message",ex.getMessage());
        return result;
    }
}
