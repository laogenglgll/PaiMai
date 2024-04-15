package com.er.paiyipai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.er.paiyipai.dao.AuctionDao;
import com.er.paiyipai.dao.HuiyuanDao;
import com.er.paiyipai.dao.JingpaiDao;
import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.model.Auction;
import com.er.paiyipai.model.Huiyuan;
import com.er.paiyipai.model.Jingpai;
import com.er.paiyipai.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2024-04-01
 */
@Service
public class AuctionServiceImpl extends ServiceImpl<AuctionDao, Auction> implements AuctionService {

    @Autowired
    private  AuctionDao auctionDao;
    @Autowired
    private HuiyuanDao huiyuanDao;
    @Autowired
    private  JingpaiDao jingpaiDao;

    @Override
    @Transactional( rollbackFor = Exception.class)
    public boolean jingPai(String hid, int jingPaiPrice, Integer aid) throws JingpaiException, InterruptedException {
        //1.根据aid，查询拍品的信息
        System.out.println(aid);
        Auction auction =auctionDao.selectById(aid);
        //1.2获取当前竞争者
        String lastHid = auction.getHid();
        //1.3把当前拍卖者保证金退回
        AuctionServiceImpl auctionService = new AuctionServiceImpl();
        if(lastHid != null){
            //2.根据编号查询会员对象
            Huiyuan huiyuan = huiyuanDao.selectById(lastHid);
            UpdateWrapper<Huiyuan> wrapper = new UpdateWrapper<>();
            wrapper.set("hyue",huiyuan.getHyue() - auction.getAbmoney());
            wrapper.set("hicemoney",huiyuan.getHicemoney() + auction.getAbmoney());
            wrapper.eq("hid",hid);
            huiyuanDao.update(null,wrapper);

            //获取版本号
            int version = auction.getGzan();
            //3.修改拍品当前竞拍者和竞拍金额
            UpdateWrapper<Auction> wrapper1 = new UpdateWrapper<>();
            wrapper1.set("hid",hid).set("nprice",jingPaiPrice).eq("aid",aid);
            Thread.sleep(1000*30);
            wrapper1.set("gzan",version + 1).eq("gzan", version);
            int i = auctionDao.update(null,wrapper1);
            if(i == 0){
                throw new JingpaiException();
            }
            //4.增加一条竞拍记录
            Jingpai log = new Jingpai();
            log.setAid(aid);
            log.setHid(hid);
            log.setJprice(jingPaiPrice);
            String temp = LocalDateTime.now().toString();

            log.setJbackup(temp.substring(0,10) + "" + temp.substring(11,19));
            int we = jingpaiDao.insert(log);
            if (we != 0) {
                return true;
            } else {
                throw new JingpaiException();
            }
        }else{

            //获取版本号
            int version = auction.getGzan();
            //3.修改拍品当前竞拍者和竞拍金额
            UpdateWrapper<Auction> wrapper1 = new UpdateWrapper<>();
            wrapper1.set("hid",hid).set("nprice",jingPaiPrice).eq("aid",aid);
            Thread.sleep(1000*30);
            wrapper1.set("gzan",version + 1).eq("gzan", version);
            auctionDao.update(null,wrapper1);
            //4.增加一条竞拍记录
            Jingpai log = new Jingpai();
            log.setAid(aid);
            log.setHid(hid);
            log.setJprice(jingPaiPrice);
            String temp = LocalDateTime.now().toString();

            log.setJbackup(temp.substring(0,10) + "" + temp.substring(11,19));
            int i = jingpaiDao.insert(log);
            if (i != 0) {
                return true;
            } else {
                throw new JingpaiException();
            }
        }
    }


    //修改竞拍表和竞拍记录表
    public  boolean pub(String hid, int jingPaiPrice, Integer aid) throws JingpaiException, InterruptedException {
        //1.根据aid，查询拍品的信息
        Auction auction =auctionDao.selectById(aid);
        //获取版本号
        int version = auction.getGzan();
        //3.修改拍品当前竞拍者和竞拍金额
        UpdateWrapper<Auction> wrapper1 = new UpdateWrapper<>();
        wrapper1.set("hid",hid).set("nprice",jingPaiPrice).eq("aid",aid);
        Thread.sleep(1000*30);
        wrapper1.set("gzan",version + 1).eq("gzan", version);
        auctionDao.update(null,wrapper1);
        //4.增加一条竞拍记录
        Jingpai log = new Jingpai();
        log.setAid(aid);
        log.setHid(hid);
        log.setJprice(jingPaiPrice);
        String temp = LocalDateTime.now().toString();

        log.setJbackup(temp.substring(0,10) + "" + temp.substring(11,19));
        int i = jingpaiDao.insert(log);
        if (i != 0) {
            return true;
        } else {
            throw new JingpaiException();
        }
    }
}
