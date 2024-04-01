package ssafy.narou.pjt.global.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssafy.narou.pjt.global.auth.dto.request.EmailCodeCheckRequest;
import ssafy.narou.pjt.global.auth.dto.request.SignUpRequest;
import ssafy.narou.pjt.global.auth.dto.response.EmailCodeResponse;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.auth.jwtAuth.JwtManager;
import ssafy.narou.pjt.global.auth.jwtAuth.TokenType;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.auth.passwordAuth.service.PasswordUserService;

import java.util.HashMap;
import java.util.Map;

import static ssafy.narou.pjt.global.auth.jwtAuth.TokenType.ACCESS_TOKEN;
import static ssafy.narou.pjt.global.auth.jwtAuth.TokenType.REFRESH_TOKEN;

@Slf4j
@RestController
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

//    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordUserService passwordUserService;
    private final JwtManager jwtManager;

    @PostMapping("/email/sendcode")
    public ResponseEntity<?> sendEmailCode(@RequestBody @Valid EmailCodeCheckRequest emailCodeCheckRequest) {
        EmailCodeResponse emailCodeResponse = passwordUserService.sendEmailCode(emailCodeCheckRequest.getEmail());
        return ResponseEntity.ok(ResponseMessage.of(true, emailCodeResponse, "인증 번호가 발송되었습니다."));
    }

//    @PostMapping("/email/codecheck")
//    public ResponseEntity<?> emailCheck(@RequestBody EmailCodeCheckRequest emailCode){
//        Map<String, Object> params = new HashMap<>();
//        params.put("email", emailCode.getEmail());
//        params.put("code", emailCode.getCode());
//        params.put("sendDate", emailCode.getSend_date());
//
//        boolean authenticated = passwordUserService.codeMatch(params);
//        return ResponseEntity.ok(ResponseMessage.of(authenticated, "인증 번호가 확인되었습니다."));
//    }

    @PutMapping("/join")
    public ResponseEntity<?> joinPasswordUser(@RequestBody @Valid SignUpRequest signUpRequest){
        Map<String, String> register = new HashMap<>();
        register.put("email", signUpRequest.getEmail());
        register.put("password", signUpRequest.getPassword());
        register.put("nickname", signUpRequest.getNickname());

        passwordUserService.signUp(register);
        return ResponseEntity.ok(ResponseMessage.of(true, "회원가입이 완료되었습니다."));
    }

    // refreshtoken 으로 accesstoken 갱신 요청
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletResponse response, @AuthenticationPrincipal NarouUserDetails userDetails){
        String access = jwtManager.createJWT(userDetails, ACCESS_TOKEN);
        String refresh = jwtManager.createJWT(userDetails, REFRESH_TOKEN);
        Cookie accessCookie = new Cookie(ACCESS_TOKEN.getName(), access);
        Cookie refreshCookie = new Cookie(REFRESH_TOKEN.getName(), refresh);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        return ResponseEntity.ok(ResponseMessage.of(true, "Token Refreshed"));
    }
}
