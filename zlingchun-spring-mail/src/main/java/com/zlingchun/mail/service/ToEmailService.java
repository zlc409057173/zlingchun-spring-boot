package com.zlingchun.mail.service;



import com.zlingchun.mail.entity.ToEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

/**
 * @author achun
 * @create 2022/6/13
 * @description 邮件发送接口
 */
public interface ToEmailService {


    /**
     * @param toEmail 发送对象
     * @desc 发送普通邮件 （无其他资源 无html 无附件）
     */
    void commonEmail(ToEmail toEmail);

    /**
     * @param toEmail 发送对象
     * @Desc 发送html形式的邮件
     */
    void htmlEmail(ToEmail toEmail) throws MessagingException;

    /**
     * 带附件 邮件发送
     */
    void enclosureEmail(ToEmail toEmail, MultipartFile multipartFile) throws MessagingException;

    /**
     * 一同发送静态资源 图片等
     * @param resId 每个资源对饮给一个Id
     */
    void staticEmail(ToEmail toEmail, MultipartFile multipartFile, String resId) throws MessagingException;
}
