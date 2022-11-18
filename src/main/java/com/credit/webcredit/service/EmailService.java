package com.credit.webcredit.service;

import com.credit.webcredit.entity.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(String toEmail);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
