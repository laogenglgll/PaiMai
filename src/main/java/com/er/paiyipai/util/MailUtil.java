package com.er.paiyipai.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    JavaMailSender javaMailSender;
    /** * 普通邮件发送 */
    public void sendSimpleMail() {
// 构建一个邮件对象
        SimpleMailMessage message=new SimpleMailMessage();
// 设置邮件主题
        message.setSubject("这是一封测试邮件");
// 设置邮件发送者，这个跟application.yml中设置的要一致message.setFrom("7*****9@qq.com");
        message.setFrom(from);
// 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        message.setTo("1599588717@qq.com");
// 设置邮件的正文
        message.setText("这是测试邮件的正文");
// 发送邮件
        javaMailSender.send(message);
    }


    /** 发送复杂邮件 */
    public void sendMimeMail(String to,String hyname,String hyid) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
//再使用这个api设置邮件
//这里true代表你要发附件邮件，只要这里为true才可以发文件
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("尊敬的"+hyname+"你好");
        String context="你爱不爱PlayBoy"+"<a href=\'Http://localhost:8080/user/jihuo?rid=1&hid="+hyid+"\'>点开有惊喜</a>";
        helper.setText(context,true);

        javaMailSender.send(message);
    }
}
