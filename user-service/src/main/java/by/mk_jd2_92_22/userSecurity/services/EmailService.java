package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.services.api.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {


    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleEmail(String toAddress, String token) {

        String message = "Пройдите по ссылке для подтверждения регистрации: " +
                "http://localhost/api/v1/users/registration?token=" + token;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject("Confirmation of registration");
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }


}
