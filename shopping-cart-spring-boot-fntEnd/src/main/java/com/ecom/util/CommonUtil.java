package com.ecom.util;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    private static String sender="arunchouhan.tk@gmail.com";

    public Boolean sendMail(String RecipientEmail, String url){
        try{

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender, "Shopping Kart");
            helper.setTo(RecipientEmail);
            helper.setSubject("Password Reset");
            log.info("Sender is " + sender+ " and Reciver is " + RecipientEmail);

            String content="<p>Hello</p>"+"<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>" + "<p><a href=\""+url+"\">Change my password</a></p>";

            helper.setText(content, true);
            mailSender.send(message);
            return true;

        }catch (Exception e){
            log.info("Email sent Failed");
            e.printStackTrace();
            return false;
        }
    }

    public static String genrateUrl(HttpServletRequest request) {
//        http://localhost:8080/forgot-password
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(),"");

    }
}
