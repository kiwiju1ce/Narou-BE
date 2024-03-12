package ssafy.narou.pjt.global.auth.passwordAuth.service;

import ssafy.narou.pjt.global.auth.dto.response.EmailCodeResponse;

import java.util.Map;

public interface PasswordUserService {

    void signUp(Map<String, String> register);
    EmailCodeResponse sendEmailCode(String email);
//    boolean codeMatch(Map<String, Object> emailCodeInfo);
}
