package com.zlingchun.mail.service.impl;

import com.zlingchun.mail.entity.ToEmail;
import com.zlingchun.mail.service.ToEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author achun
 * @create 2022/6/13
 * @description descrip
 */
@Service
@Slf4j
public class ToEmailServiceImpl implements ToEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送普通邮件
     *
     * @param toEmail 发送对象
     * @return 统一ajax
     */
    @Override
    public void commonEmail(ToEmail toEmail) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(toEmail.getTos());
        //邮件标题
        message.setSubject(toEmail.getSubject());
        //邮件内容
        message.setText(toEmail.getContent());
        mailSender.send(message);
    }

    /**
     * 发送Html 有机胺
     *
     * @param toEmail 发送对象
     */
    @Override
    public void htmlEmail(ToEmail toEmail) throws MessagingException {
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
        //谁发
        minehelper.setFrom(from);
        //谁要接收
        minehelper.setTo(toEmail.getTos());
        //邮件主题
        minehelper.setSubject(toEmail.getSubject());
        //邮件内容   true 表示带有附件或html
        minehelper.setText(toEmail.getContent(), true);
        mailSender.send(message);
    }


    /**
     * 带附件邮件发送
     */
    @Override
    public void enclosureEmail(ToEmail toEmail, MultipartFile multipartFile) throws MessagingException {
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //谁发
        helper.setFrom(from);
        //谁接收
        helper.setTo(toEmail.getTos());
        //邮件主题
        helper.setSubject(toEmail.getSubject());
        //邮件内容   true 表示带有附件或html
        helper.setText(toEmail.getContent(), true);
        File multipartFileToFile = multipartFileToFile(multipartFile);
        FileSystemResource file = new FileSystemResource(multipartFileToFile);
        String filename = file.getFilename();
        helper.addAttachment(filename, file);//添加附件
        mailSender.send(message);
    }

    /**
     * 发送静态资源
     *
     * @param resId 每个资源对饮给一个Id
     */
    @Override
    public void staticEmail(ToEmail toEmail, MultipartFile multipartFile, String resId) throws MessagingException{
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //谁发
        helper.setFrom(from);
        //谁接收
        helper.setTo(toEmail.getTos());
        //邮件主题
        helper.setSubject(toEmail.getSubject());
        //邮件内容   true 表示带有附件或html
        //邮件内容拼接
        String content =
                "<html><body><p>"+toEmail.getContent()+"</p><img width='250px' src=\'cid:" + resId + "\'</body></html>";
        helper.setText(content, true);
        //蒋 multpartfile 转为file
        if(!multipartFile.isEmpty()){
            File multipartFileToFile = multipartFileToFile(multipartFile);
            FileSystemResource res = new FileSystemResource(multipartFileToFile);

            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(resId, res);
        }
        mailSender.send(message);
    }


    /**
     * 将 multpartfile 转为file
     *
     * @return file
     */
    private File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}