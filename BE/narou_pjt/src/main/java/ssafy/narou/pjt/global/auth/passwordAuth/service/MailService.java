package ssafy.narou.pjt.global.auth.passwordAuth.service;

public interface MailService {
    void sendEmail(String authEmail, String title, String content);
}
