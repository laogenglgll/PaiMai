package com.er.paiyipai.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.er.paiyipai.model.Huiyuan;
import com.er.paiyipai.service.HuiyuanService;
import com.er.paiyipai.util.TokenUtil;
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
@RequestMapping("/user")
public class HuiyuanController {
    @Autowired
    private HuiyuanService huiyuanService;

    @RequestMapping("register")
    public ResponData register(@RequestBody Huiyuan hy){
        huiyuanService.save(hy);
        System.out.println("123");
        ResponData responData = new ResponData(1,"ok");
        System.out.println(responData);
        System.out.println(123);
        return responData;
    }

    @RequestMapping("login")
    public ResponData login(String username,String pwd) throws Exception {
        ResponData responData = new ResponData();

        Huiyuan hy = huiyuanService.getById(username);

        if(hy != null){
            if(hy.getHpwd().equals(pwd)){
                if(hy.getRid() == 1){
                    String token = TokenUtil.sign(username);
                    responData.setCode(1);
                    responData.setMsg(token);
                    return responData;
                }else {
                    responData.setMsg("会员账号未激活");
                }
            }else{
               responData.setMsg("会员密码错误");
            }
        }else {
            responData.setMsg("会员账号不存在");
        }
        return responData;
    }

    //查询某个会员
    @RequestMapping("getone")
    public Huiyuan getOne(Integer huiyuanid){
        return huiyuanService.getById(huiyuanid);
    }

    //修改密码
    @RequestMapping("updatepwd")
    public ResponData updatePwd(String hid,String hpwd,String newpwd){
        ResponData responData = new ResponData();
        //根据id查询
        Huiyuan huiyuan = huiyuanService.getById(hid);
        //判断旧密码是否相等
        if(huiyuan.getHpwd() != hpwd){
            responData.setCode(-1);
            responData.setMsg("旧密码不正确");
        }else {
            //设置修改信息
            UpdateWrapper<Huiyuan> wrapper = new UpdateWrapper<>();
            wrapper.set("hpwd",hpwd).eq("hid",hid);
            boolean flag = huiyuanService.update(wrapper);
            if(flag){
                responData.setCode(1);
                responData.setMsg("修改成功");
            }else{
                responData.setCode(-1);
                responData.setMsg("修改失败");
            }
        }
        return responData;
    }

}

