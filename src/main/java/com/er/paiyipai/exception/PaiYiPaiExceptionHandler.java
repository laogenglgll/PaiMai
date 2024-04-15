package com.er.paiyipai.exception;

import com.er.paiyipai.web.ResponData;

import javax.servlet.http.HttpServletRequest;
// TODO:未完成

//@RestControllerAdvice
public class PaiYiPaiExceptionHandler {
   // @ExceptionHandler
    public Object hander1(HttpServletRequest request,Exception e){
        ResponData responData = new ResponData(-1,"抱歉服务器端出错");
        e.printStackTrace();
        return null;
    }
}
