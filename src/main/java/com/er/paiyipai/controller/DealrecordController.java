package com.er.paiyipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.er.paiyipai.model.Dealrecord;
import com.er.paiyipai.service.AuctionService;
import com.er.paiyipai.service.DealrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@RestController
@RequestMapping("/dealrecord")
public class DealrecordController {
    @Autowired
    private DealrecordService dealrecordService;
    @Autowired
    private AuctionService auctionService;

    //查询我的订单
    @RequestMapping("myorder")
    public List<Dealrecord> myOrder(String hid) throws ParseException {
        System.out.println(hid+"12312312312311111111");
        //设置查询条件
        QueryWrapper<Dealrecord> wrapper = new QueryWrapper<>();
        wrapper.eq("hid",hid);
        //获取该用户所有的订单
        List<Dealrecord> list = dealrecordService.list(wrapper);
        for(Dealrecord dealrecord:list){
            String time = dealrecord.getCjtime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date chengJiaoTime = df.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(chengJiaoTime);
            calendar.add(Calendar.DAY_OF_MONTH,3);
            Date jieZhiTime = calendar.getTime();
            String jieZhiTimeS = df.format(jieZhiTime);
            dealrecord.setJieZhiTime(jieZhiTimeS);

            //设置图片地址
            String pic = auctionService.getById(dealrecord.getAid()).getGpic();
            dealrecord.setPic(pic);
        }
        return list;
    }
}

