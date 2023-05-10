package com.example.course_work.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Transactional
@Service
public class EmailService {
    @Async
    public void sendEmail(String subject, String text, String email) {
        Properties properties = System.getProperties();
        //конфигцурация почтового клиента
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "smtp.mail.ru");
        properties.setProperty("mail.smtp.user", "javaformail@mail.ru");
        properties.setProperty("mail.smtp.password", "BryYqAyDiuv4MxhUj02k");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("javaformail@mail.ru", "BryYqAyDiuv4MxhUj02k");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("javaformail@mail.ru"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException mex){ mex.printStackTrace();}
    }
}



