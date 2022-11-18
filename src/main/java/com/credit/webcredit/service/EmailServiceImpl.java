package com.credit.webcredit.service;

import com.credit.webcredit.entity.EmailDetails;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public String ramdomPassword(){
        String token = RandomString.make(8);
        return token;
    }
    // Method 1
    // To send a simple email
    @Override
    public String sendSimpleMail(String toEmail)
    {

        // Try block to check for exceptions
        try {
            Random generator = new Random();
            int random = generator.nextInt();
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(toEmail);
            mailMessage.setText(ramdomPassword());
             mailMessage.setSubject("Mật khẩu mới của bạn là: ");
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}
