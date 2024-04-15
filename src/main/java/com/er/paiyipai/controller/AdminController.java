package com.er.paiyipai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.er.paiyipai.model.Admin;
import com.er.paiyipai.service.AdminService;
import com.er.paiyipai.web.ResponData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("admin")
public class AdminController {
    private static final int PAGE_NUM = 2;
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public ResponData login(@RequestBody Admin ad){
        ResponData result = new ResponData();

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",ad.getAid());
        wrapper.eq("apwd",ad.getApwd());
        Admin admin = adminService.getOne(wrapper);

        if(admin == null) {
            result.setCode(-1);
            result.setMsg("用户名或者密码错误");
        }else{
            result.setCode(1);
            result.setMsg("ok");
        }
        return result;
    }

    //注册
    @RequestMapping("register")
    public ResponData register(@RequestBody Admin admin){
        ResponData responData = new ResponData(1,"ok");
        adminService.save(admin);
        return responData;
    }

    //查询所有的管理员
    @RequestMapping("list")
    public Page<Admin> list(int pageno){
        //设置分页条件
        Page<Admin> page = new Page<>(pageno,PAGE_NUM);
        //获取list
        Page<Admin> list = adminService.page(page);
        return list;
    }

    //修改
    @RequestMapping("update")
    public ResponData update(@RequestBody Admin admin){
        ResponData result = new ResponData();
        //设置修改条件
        UpdateWrapper<Admin> wrapper = new UpdateWrapper<>();
        wrapper.set("aname",admin.getAname());
        wrapper.set("rid",admin.getRid());
        wrapper.set("abackup",admin.getAbackup());
        boolean flag = adminService.update(wrapper);
        if(flag == true){
            result.setCode(1);
        }else {
            result.setCode(-1);
            result.setMsg("修改失败");
        }
        return result;
    }

    //删除
    @RequestMapping("delete")
    public ResponData delete(Integer aid){
        //设置删除条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("aid",aid);
        ResponData result = new ResponData();

        boolean flag =  adminService.remove(wrapper);
        if(flag == true){
            result.setCode(1);
        }else {
            result.setCode(-1);
            result.setMsg("删除失败");
        }
        return result;

    }
}

