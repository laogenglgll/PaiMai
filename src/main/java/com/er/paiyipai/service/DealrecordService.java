package com.er.paiyipai.service;

import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.model.Dealrecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
public interface DealrecordService extends IService<Dealrecord> {

    void weiYue();

    void fuKuan(Integer cjid) throws JingpaiException;
}
