package ru.vl7sha.digitalspring2024registrationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Token;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.TokenType;

@Service
@RequiredArgsConstructor
public class MailService {
    private static final String CONFIRM_TOKEN_SUBJECT = "Активируйте аккаунт";
    private static final String RESTORE_PASSWORD_SUBJECT = "Восстановление пароля";

    private final JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String mailFrom;
    @Value("${urls.confirm-token-url}")
    private String frontendUrlConfirm;
    @Value("${urls.restore-token-url}")
    private String frontendUrlRestore;

    public void sendMessage(String email, String subject, String msg) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "UTF-8");

        try {
            helper.setFrom(mailFrom);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(msg, true);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendToken(String email, Token token) {
        sendMessage(
                email,
                token.getTokenType() == TokenType.CONFIRM ? CONFIRM_TOKEN_SUBJECT : RESTORE_PASSWORD_SUBJECT,
                buildConfirmTokenMsg(token)
        );
    }

    private String buildConfirmTokenMsg(Token token) {
        return "<a href=\"" + frontendUrlConfirm +
                "?token=" + token.getToken() +
                "\">Подтвердить регистрацию<a/>";
    }

    private String buildRestorePasswordTokenMsg(Token token){
        return "Перейти по ссылке для установки нового пароля, чтобы продолжить работу с платформой:" +
                " <a href=\"" + frontendUrlRestore +
                "?token=" + token.getToken() +
                "\"><a/>";
    }
}