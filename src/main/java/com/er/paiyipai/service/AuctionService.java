package com.er.paiyipai.service;

import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.model.Auction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
public interface AuctionService extends IService<Auction> {

    boolean jingPai(String hid, int jingPaiPrice, Integer rid) throws JingpaiException, InterruptedException;
}
