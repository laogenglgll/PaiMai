package com.er.paiyipai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.er.paiyipai.dao.AuctionDao;
import com.er.paiyipai.dao.DealrecordDao;
import com.er.paiyipai.dao.HuiyuanDao;
import com.er.paiyipai.dao.MoneyrecordDao;
import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.model.Dealrecord;
import com.er.paiyipai.model.Huiyuan;
import com.er.paiyipai.model.Moneyrecord;
import com.er.paiyipai.service.DealrecordService;
import com.er.paiyipai.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@Service
public class DealrecordServiceImpl extends ServiceImpl<DealrecordDao, Dealrecord> implements DealrecordService {
    @Autowired
    private DealrecordDao dealrecordDao;
    @Autowired
    private AuctionDao auctionDao;
    @Autowired
    private HuiyuanDao huiyuanDao;
    @Autowired
    private MoneyrecordDao moneyrecordDao;
    @Override
    public void weiYue() {
        System.out.println("job2");
        //获取
        Calendar calendar = Calendar.getInstance();
        //获取三天前
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        Date date = calendar.getTime();
        //变成字符串
        String SDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(SDate);
        //找到违约记录
        QueryWrapper<Dealrecord> wrapper = new QueryWrapper<>();
        //设置条件
        wrapper.eq("cjtime",SDate).eq("did", Constant.DEAL_STATE_DFK);
        List<Dealrecord> list = dealrecordDao.selectList(wrapper);
        //修改状态
        UpdateWrapper<Dealrecord> wrapper1 = new UpdateWrapper<>();
        //设置条件
        wrapper1.set("did",Constant.DEAL_STATE_WY).eq("cjtime",SDate).eq("did", Constant.DEAL_STATE_DFK);
        int num = dealrecordDao.update(null,wrapper1);
        for(Dealrecord dealrecord:list){
            //获取违约金额
            int abmoney = auctionDao.selectById(dealrecord.getAid()).getAbmoney();
            //找到违约对象
            Huiyuan huiyuan = huiyuanDao.selectById(dealrecord.getHid());
            //修改违约金
            UpdateWrapper<Huiyuan> wrapper2 = new UpdateWrapper<>();
            wrapper2.eq("hid",huiyuan.getHid()).set("hicemoney",huiyuan.getHicemoney() - abmoney);
            huiyuanDao.update(null,wrapper2);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fuKuan(Integer cjid) throws JingpaiException {
        //查询要成交的记录
        Dealrecord dealrecord = dealrecordDao.selectById(cjid);
        //查询会员
        Huiyuan huiyuan  = huiyuanDao.selectById(dealrecord.getHid());
        //查询账户余额
        int hyue = huiyuan.getHyue();
        //查询冻结金
        int incemoney = huiyuan.getHicemoney();
        //成交金额
        int cjMoney = dealrecord.getCjmoney();
        //得到保证金
        int abmoney = auctionDao.selectById(dealrecord.getAid()).getAbmoney();
        //1.修改会员的余额和保证金
        //余额不足时候
        if(! (hyue > dealrecord.getCjmoney())){
            throw new JingpaiException();
        }
        //余额足够
        UpdateWrapper<Huiyuan> wrapperH = new UpdateWrapper<>();
        wrapperH.set("incemoney",incemoney - abmoney).set("hyue",hyue - cjMoney + abmoney)
                .eq("hid",huiyuan.getHid());
        huiyuanDao.update(null,wrapperH);

        //2.修改交易记录表 4---》2
        UpdateWrapper<Dealrecord> wrapperD = new UpdateWrapper<>();
        wrapperD.eq("cjid",cjid)
                .set("did",Constant.DEAL_STATE_CJ);
        dealrecordDao.update(null,wrapperD);

        //新增一条消费记录
        Moneyrecord moneyrecord = new Moneyrecord();
        moneyrecord.setHid(huiyuan.getHid());
        moneyrecord.setHname((huiyuan.getHname()));
        moneyrecord.setCjtime(LocalDate.now().toString());
        moneyrecord.setCjmoney(dealrecord.getCjmoney());
        moneyrecord.setMtype(Constant.MONEY_RECORD_TYPE_CONSUME);
        moneyrecordDao.insert(moneyrecord);
    }
}
