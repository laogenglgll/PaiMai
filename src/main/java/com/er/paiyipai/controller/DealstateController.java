package com.er.paiyipai.controller;


import com.er.paiyipai.exception.JingpaiException;
import com.er.paiyipai.service.DealrecordService;
import com.er.paiyipai.web.ResponData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@RestController
@RequestMapping("/dealstate")
public class DealstateController {
    @Autowired
    private DealrecordService dealrecordService;
    @RequestMapping("fukuan")
    public ResponData fuKuan(Integer cjid) throws JingpaiException {
        dealrecordService.fuKuan(cjid);
        return new ResponData(1,"ok");
    }


}

