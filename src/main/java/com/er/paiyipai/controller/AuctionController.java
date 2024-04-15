package com.er.paiyipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.model.Auction;
import com.er.paiyipai.model.Ptypes;
import com.er.paiyipai.service.AuctionService;
import com.er.paiyipai.service.PtypesService;
import com.er.paiyipai.util.Constant;
import com.er.paiyipai.web.ResponData;
import com.er.paiyipai.websocket.PaiMaiWebSocket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private PtypesService ptypesService;

    @RequestMapping("/save")
    public ResponData auctionSave(Auction auction, MultipartFile pic) throws IOException {
        String fileName = UUID.randomUUID().toString();
        //获取图片的名字
        String ext = pic.getOriginalFilename().substring(pic.getOriginalFilename().lastIndexOf("."));
        //创建文件夹对象
        File direct = new File("D:\\F\\nginx-1.10.1\\html\\images");
        //具体的文件对象
        File pic1 = new File(direct,fileName + ext);
        pic.transferTo(pic1);
        //设置图片的路径
        auction.setGpic("images/"+fileName + ext);
        auction.setGzan(0);
        auction.setAnum(0);
        auction.setState(Constant.AUC_STATE_UNREVIEW);
        auction.setCflag(Constant.AUC_CREATOR_HY);
        auctionService.save(auction);
        ResponData responData = new ResponData(1,"ok");
        return  responData;
    }

    @RequestMapping("list")
    public Page<Auction> page(int pageno,int state){
        Page<Auction> page = new Page<>(pageno,2);
        //查询条件
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("state",state);
        Page<Auction> result = auctionService.page(page,wrapper);
        //得到当前页
        List<Auction> list = result.getRecords();
        //设置对象的类型
        for(Auction data:list){
            //获得id
            int tid = data.getTid();
            //根据id查询类型
            Ptypes type = ptypesService.getById(tid);
            //把类型添加到结果上
            data.setType(type);
        }
        return result;
    }

    @RequestMapping("shenhe")
    public ResponData shenhe(String aid,int state){
        ResponData result = new ResponData(1,"ok");
        UpdateWrapper<Auction> webServer = new UpdateWrapper<>();
        webServer.set("state",state).eq("aid",aid);
        boolean flag = auctionService.update(webServer);
        if(flag == false){
            result.setCode(-1);
            result.setMsg("error");
        }
        return result;
    }


    //查询正在拍卖的
    @RequestMapping("ing")
    public Page<Auction> ing(int pageno,int state,String tid,String gname){
        QueryWrapper<Auction> wrapper = new QueryWrapper<>();
        wrapper.eq("state",state);
        if(tid != null && !tid.equals("")){
            wrapper.eq("tid",Integer.parseInt(tid));
        }
        wrapper.eq(StringUtils.isNoneBlank(gname),"gname",gname);
        Page<Auction> page = new Page<Auction>(pageno,2);
        Page<Auction> result = auctionService.page(page,wrapper);
        return result;
    }
    //根据aid进行查询
    @RequestMapping("selection")
    public Auction selection(Integer aid){
        return auctionService.getById(aid);
    }

    //竞拍过程
    @RequestMapping("jingpai")
    public ResponData jingpai(String hid,int jingpaiprice,String aid) throws JingpaiException, InterruptedException {
        ResponData result = new ResponData();
        System.out.println(hid +"/"+jingpaiprice+"/"+aid);
        PaiMaiWebSocket.sendPrice(hid,jingpaiprice, Integer.parseInt(aid));
        boolean flag = auctionService.jingPai(hid,jingpaiprice, Integer.valueOf(aid));
        if(flag){
            result.setCode(1);
        }
        return result;
    }



}

