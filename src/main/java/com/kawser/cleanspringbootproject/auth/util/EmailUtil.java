package com.kawser.cleanspringbootproject.auth.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for sending emails.
 */
@Component
public class EmailUtil {

    /**
     * JavaMailSender instance to send emails.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email with the OTP to verify the account.
     * @param email email to send the OTP
     * @param otp OTP to verify the account
     * @throws MessagingException if an error occurs while sending the email
     */
    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Verify your account");
        helper.setText(
                        """
                        <div>
                        <a href="https://localhost:8080/auth/verify-account?email=%s&token=%s">
                        Click here to verify your account
                        </a>
                        </div>
                        """
                        .formatted(email, otp),
                true);

        mailSender.send(message);
    }

    /**
     * Sends an email with the OTP to recover the password.
     * @param email email to send the OTP
     * @param otp OTP to recover the password
     * @throws MessagingException if an error occurs while sending the email
     */
    public void sendRecoverPasswordEmail(String email, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Recover your password");
        helper.setText(
                """
                        <div>
                        <a href="http://localhost:8080/password/reset?email=%s&token=%s">
                        Click here to set a new password
                        </a>
                        </div>
                        """
                        .formatted(email, otp),
                true);
        
        mailSender.send(message);        
    }
}
