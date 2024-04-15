package com.er.paiyipai;

import com.er.paiyipai.model.Huiyuan;
import com.er.paiyipai.util.CacheService;
import com.er.paiyipai.util.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class PaiyipaiApplicationTests {

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private CacheService cacheService;
    @Test
    void contextLoads() {
    }

    @Test
    void mail(@RequestBody Huiyuan yh) throws MessagingException {
        mailUtil.sendMimeMail(yh.getHemail(),yh.getHname(), yh.getHid());
    }


    @Test
    public void testcache(){
        cacheService.add("name","zhaoyihui");
        HashMap<String,String> obj = new HashMap<>();
        obj.put("赵益慧","海城");
        cacheService.add("map",obj);
        List<String> list = new ArrayList<>();
        list.add("GenYunLong");
        cacheService.add("list",list);
        System.out.println(cacheService.get("name"));
        System.out.println(cacheService.getObject("map",HashMap.class));
        System.out.println(cacheService.getList("list",String.class));
    }


}
