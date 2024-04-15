package com.er.paiyipai.controller;


import com.er.paiyipai.model.Ptypes;
import com.er.paiyipai.service.PtypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/ptypes")
public class PtypesController {

    @Autowired
    private PtypesService ptypesService;

    @RequestMapping("list")
    public List<Ptypes> getAllPtypes(){
        return ptypesService.list();
    }

}

