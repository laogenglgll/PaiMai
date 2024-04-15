package com.er.paiyipai.timejob;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.er.paiyipai.dao.AuctionDao;
import com.er.paiyipai.model.Auction;
import com.er.paiyipai.model.Dealrecord;
import com.er.paiyipai.service.DealrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Job {
    @Autowired
    private AuctionDao auctionDao;
    @Autowired
    private DealrecordService dealrecordService;
    //接受竞拍
    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional
    public void endJingPai(){
        String now = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("etime",now);
        wrapper.eq("state",4);
        List<Auction> list =  auctionDao.selectList(wrapper);
        for(Auction auction:list){
            auction.setState(5);
            auctionDao.updateById(auction);
            Dealrecord deal = new Dealrecord();
            deal.setAid(auction.getAid());
            deal.setHid(auction.getHid());
            deal.setCjmoney(auction.getAbmoney());
            deal.setGname(auction.getGname());
            if(auction.getHid() == null){
                deal.setDid(1);
            }else{
                deal.setDid(2);
            }
        }
        System.out.println("定时任务");
    }

    @Scheduled(cron = "0 1/1 * * * ?")
    public void weiYueJob(){
        dealrecordService.weiYue();
    }
}
