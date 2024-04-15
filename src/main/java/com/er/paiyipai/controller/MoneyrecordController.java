package com.er.paiyipai.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.er.paiyipai.model.Huiyuan;
import com.er.paiyipai.model.Moneyrecord;
import com.er.paiyipai.service.HuiyuanService;
import com.er.paiyipai.service.MoneyrecordService;
import com.er.paiyipai.util.Constant;
import com.er.paiyipai.web.ResponData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@RestController
@RequestMapping("/moneyrecord")
public class MoneyrecordController {

    @Autowired
    private HuiyuanService huiyuanService;
    @Autowired
    private MoneyrecordService moneyrecordService;

    @RequestMapping("yue")
    public Huiyuan yuE(Integer hid){
        return huiyuanService.getById(hid);
    }

    @RequestMapping("change")
    public ResponData change(String hid,Integer price){
        //修改会员的余额
        UpdateWrapper<Huiyuan> wrapper = new UpdateWrapper<>();
        wrapper.eq("hid",hid).set("hyue",huiyuanService.getById(hid).getHyue() + price);
        huiyuanService.update(wrapper);

        //在消费记录表增加新的记录
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setMtype(Constant.MONEY_RECORD_TYPE_ADD);
        moneyrecord.setCjmoney(price);
        moneyrecord.setHname(huiyuanService.getById(hid).getHname());
        moneyrecord.setCjtime(LocalDate.now().toString());
        moneyrecord.setHid(String.valueOf(hid));
        moneyrecordService.save(moneyrecord);
        return new ResponData(1,"ok");
    }

    @RequestMapping("out")
    public ResponData out(String hid,Integer price){
        //修改会员的余额
        UpdateWrapper<Huiyuan> wrapper = new UpdateWrapper<>();
        wrapper.eq("hid",hid).set("hyue",huiyuanService.getById(hid).getHyue() - price);
        huiyuanService.update(wrapper);

        //在消费记录表增加新的记录
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setMtype(Constant.MONEY_RECORD_TYPE_OUT);
        moneyrecord.setCjmoney(price);
        moneyrecord.setHname(huiyuanService.getById(hid).getHname());
        moneyrecord.setCjtime(LocalDate.now().toString());
        moneyrecord.setHid(String.valueOf(hid));
        moneyrecordService.save(moneyrecord);
        return new ResponData(1,"ok");
    }

    @RequestMapping("getlist")
    public Page<Moneyrecord> getList(Integer current){
        Page<Moneyrecord> page = new Page<>(current,2);
        return moneyrecordService.page(page);
    }
}

