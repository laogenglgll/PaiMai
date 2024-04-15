package com.er.paiyipai.exception;

import com.er.paiyipai.web.ResponData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class jingPaiExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponData handler1(HttpServletRequest request,Exception e){
        e.printStackTrace();
        ResponData responData = new ResponData(-1,"服务器有异常");
        return responData;
    }

    @ExceptionHandler(value = JingpaiException.class)
    public ResponData handler2(HttpServletRequest request,Exception e){
        e.printStackTrace();
        ResponData responData = new ResponData(-1,e.getMessage());
        return responData;
    }
}
