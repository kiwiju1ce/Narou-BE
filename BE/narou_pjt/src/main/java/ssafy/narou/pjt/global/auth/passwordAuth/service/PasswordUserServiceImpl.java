package ssafy.narou.pjt.global.auth.passwordAuth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.global.auth.passwordAuth.entity.PasswordLoginUser;
import ssafy.narou.pjt.global.error.EmailDuplicationException;
import ssafy.narou.pjt.member.repository.MemberRepository;
import ssafy.narou.pjt.member.service.MemberService;
import ssafy.narou.pjt.global.auth.dto.response.EmailCodeResponse;
import ssafy.narou.pjt.global.auth.passwordAuth.repository.PasswordMemberRepository;
import ssafy.narou.pjt.global.properties.EmailContent;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordUserServiceImpl implements PasswordUserService{

    @Value("${code.expiration}")
    private Long EMAIL_CODE_EXPIRATION;
    private static final SecureRandom secureRandom = new SecureRandom();
    private final MailService mailService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordMemberRepository passwordMemberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void signUp(Map<String, String> register) {
        String encoded = passwordEncoder.encode(register.get("password"));
        Member passwordUser = Member.createPasswordUser(register.get("email"), register.get("nickname"));
        Member member = memberRepository.save(passwordUser);

        passwordMemberRepository.save(
                PasswordLoginUser.of(member, encoded)
        );
    }

    @Override
    public EmailCodeResponse sendEmailCode(String email) {
        if (memberService.emailDupCheck(email)){
            throw new EmailDuplicationException("이미 등록된 이메일입니다.");
        }
        log.info("sending email code");
        String code = generateSixRandomNumber();
        mailService.sendEmail(email,
                EmailContent.EMAIL_TITLE,
                EmailContent.EMAIL_CONTENT + code);
        log.info("sent email code");
        return EmailCodeResponse.builder()
                .email(email)
                .code(code)
                .created_time(LocalDateTime.now())
                .build();
    }

//    @Override
//    public boolean codeMatch(Map<String, Object> emailCodeInfo) {
//        String email = (String) emailCodeInfo.get("email");
//
//        Optional<EmailCodeResponse> emailCode = authenticationCodeRepository.findbyEmail(email);
//        boolean authenticated = emailCode.filter(codeInfo -> TimeUtils.checkExpiration(codeInfo.getCreated_time(), EMAIL_CODE_EXPIRATION))
//                                        .filter(code -> code.getCode().equals(emailCodeInfo.get("code")))
//                                        .isEmpty();
//        if (authenticated) return true;
//        else throw new EmailCodeNotMatchesException("인증 번호가 일치하지 않습니다.");
//    }

    private String generateSixRandomNumber() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
