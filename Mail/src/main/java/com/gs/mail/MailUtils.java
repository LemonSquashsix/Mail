package com.gs.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class MailUtils {

    public static void sendMail(Mail mail) {
        Session session = Session.getInstance(MailConfig.build("src/main/resources/mail.properties"),
                new MailAuthenticator(MailConfig.getString("username"), MailConfig.getString("password")));
        mail.setFrom(MailConfig.getString("username"));
        try {
            Transport transport = session.getTransport(); // 获取传输对象
            transport.connect();
            Message message = new MimeMessage(session); // 邮件消息对象
            message.setFrom(mail.getFrom()); // 发送者
            message.setSubject(mail.getSubject());
            if (mail.getContent() != null) {
                message.setContent(mail.getContent(), mail.getContentType());
            } else {
                message.setContent(mail.getMultipart());
            }

            message.setRecipients(Message.RecipientType.TO, mail.getTo());
            message.setRecipients(Message.RecipientType.CC, mail.getCc());
            message.setRecipients(Message.RecipientType.BCC, mail.getBcc());

            transport.sendMessage(message, message.getAllRecipients()); // 发送邮件
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setSubject("放假啦");
        // mail.setContent("放假8天<img src='https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2926548019,958494129&fm=27&gp=0.jpg'/>");
        mail.setContentType("text/html;charset=utf-8");
        mail.setBodyContent("放假啦");
        List<String> files = new ArrayList<String>();
        files.add("src/main/resources/mail.properties");
        files.add("src/main/resources/antlr-2.7.2.pom");
        mail.setFilePathes(files);
        mail.setTo("255226259@qq.com");
        mail.setCc("342342@126.com");
        mail.setBcc("5432523@126.com");
        MailUtils.sendMail(mail);
    }

}
