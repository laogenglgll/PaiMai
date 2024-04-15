package com.er.paiyipai.util;

public class Constant {
    //商品的状态
    public static final int AUC_STATE_UNREVIEW=1;
    public static final int AUC_STATE_REVIEW_PASS=2;
    public static final int AUC_STATE_REVIEW_REFUSE=3;
    public static final int AUC_STATE_AUC_ING=4;
    public static final int AUC_STATE_END=5;

    //发布者角色
    public static final int AUC_CREATOR_HY=1;
    public static final int AUC_CREATE_ADMIN=2;



    public static final int DEAL_STATE_DFK = 4;//代付款
    public static final int DEAL_STATE_WY = 3;//违约
    public static final Object DEAL_STATE_CJ =2 ; //交易完成


    //消费表中
    public static final Integer MONEY_RECORD_TYPE_ADD = 0;//存款
    public static final Integer MONEY_RECORD_TYPE_OUT = 1;//体现

    public static final Integer MONEY_RECORD_TYPE_CONSUME = 2;//竞拍消费
}
