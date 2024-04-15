package com.er.paiyipai.exception;

public class JingpaiException extends Exception{
    public JingpaiException(String msg){
        super(msg);
    }
    public JingpaiException(){
        super("多人同事竞拍，你手慢了");
    }
}
