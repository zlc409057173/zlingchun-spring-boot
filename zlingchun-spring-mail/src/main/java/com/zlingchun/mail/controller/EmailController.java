package com.zlingchun.mail.controller;

import com.zlingchun.mail.entity.ToEmail;
import com.zlingchun.mail.response.JsonReturn;
import com.zlingchun.mail.service.ToEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Arrays;

/**
 * @author achun
 * @create 2022/6/13
 * @description descrip
 */
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private ToEmailService toEmailService;

    /**
     * 测试普通邮件发送
     */
    @PostMapping("/common")
    public JsonReturn common(ToEmail toEmail) {
        try {
            toEmailService.commonEmail(toEmail);
            return JsonReturn.buildSuccess(toEmail.getTos(), "发送普通邮件成功");
        } catch (MailException e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("普通邮件方失败");
        }
    }

    /**
     * html 类型邮件发送
     */
    @PostMapping("/html")
    public JsonReturn html(ToEmail toEmail) {
        try {
            toEmailService.htmlEmail(toEmail);
            return JsonReturn.buildSuccess(Arrays.toString(toEmail.getTos()) + toEmail.getContent(), "HTML邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("HTML邮件失败");
        }
    }

    /**
     * 发送附件 邮件发送
     */
    @PostMapping("/enclosure")
    public JsonReturn enclosureEmail(ToEmail toEmail, @RequestPart MultipartFile multipartFile) {
        try {
            toEmailService.enclosureEmail(toEmail, multipartFile);
            return JsonReturn.buildSuccess(Arrays.toString(toEmail.getTos()) + toEmail.getContent(), "附件邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("附件邮件发送失败" + e.getMessage());
        }
    }

    /**
     * 发送包含静态资源的文件
     *
     * @param toEmail 接收方
     * @param multipartFile 静态资源
     * @param resId 资源唯一Id(随意传，唯一即可）
     */
    @PostMapping("/static")
    public JsonReturn Static(ToEmail toEmail, MultipartFile multipartFile, String resId) {
        try {
            toEmailService.staticEmail(toEmail, multipartFile, resId);
            return JsonReturn.buildSuccess(Arrays.toString(toEmail.getTos()) + toEmail.getContent(), "嵌入静态资源的邮件已经发送");
        } catch (MessagingException e) {
            return JsonReturn.buildFailure("嵌入静态资源的邮件发送失败");
        }
    }
}
