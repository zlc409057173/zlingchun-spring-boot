package com.zlingchun.mail.utils;

import com.zlingchun.mail.entity.ToEmail;
import com.zlingchun.mail.service.ToEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author achun
 * @create 2022/6/13
 * @description descrip
 */
@SpringBootTest
public class EmailSenderTest {
    @Autowired
    ToEmailService toEmailService;
    @Test
    public void testHtml() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h1>这是Html格式邮件!</h1>\n" +
                "</body>\n" +
                "</html>";
        toEmailService.htmlEmail(new ToEmail(new String[]{"13337227132@163.com"},"Html邮件",content));
    }

}
