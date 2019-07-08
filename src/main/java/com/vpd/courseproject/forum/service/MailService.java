package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class MailService implements IMailService {
    private static final MailService instance = new MailService();
    private Properties properties = new Properties();

    private MailService(){}

    public static MailService getInstance() {return instance; }

    public void sendMailForPasswordRecovery(User user, String lang) throws ServiceException{
        try {
            properties.load(Objects.requireNonNull(MailService.class.getClassLoader().getResourceAsStream("mail.properties")));
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        String name;
        String subject;
        String text;
        if (lang.equals("en")) {
            name = properties.getProperty("ENG_MAIL_NAME");
            subject = properties.getProperty("ENG_SUBJECT");
            text = String.format(properties.getProperty("ENG_TEXT"), user.getName(), user.getLogin(), user.getPassword());
        } else {
            name = properties.getProperty("RUS_MAIL_NAME");
            subject = properties.getProperty("RUS_SUBJECT");
            text = String.format(properties.getProperty("RUS_TEXT"), user.getName(), user.getLogin(), user.getPassword());
        }

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty("mail.from"),
                                properties.getProperty("mail.smtp.password"));
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.from"), name));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
