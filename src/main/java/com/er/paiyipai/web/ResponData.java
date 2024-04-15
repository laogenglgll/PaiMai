package com.er.paiyipai.web;

import lombok.Data;

@Data
public class ResponData {
    //1成功 -1失败
    private int code;

    private String msg;

    //带参数构造方法
    public ResponData(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResponData(){

    }
}
