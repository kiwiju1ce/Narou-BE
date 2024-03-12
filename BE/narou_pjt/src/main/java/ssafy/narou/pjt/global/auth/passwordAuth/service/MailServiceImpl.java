package ssafy.narou.pjt.global.auth.passwordAuth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ssafy.narou.pjt.global.error.EmailServerException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String authEmail, String title, String content) {
        SimpleMailMessage emailForm = createEmailForm(authEmail, title, content);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new EmailServerException("인증 번호 발송에 실패했습니다.");
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}