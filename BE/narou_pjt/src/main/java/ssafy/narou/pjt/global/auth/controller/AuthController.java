package ssafy.narou.pjt.global.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.auth.passwordAuth.service.PasswordUserService;
import ssafy.narou.pjt.global.auth.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordUserService passwordUserService;

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
        jwtTokenProvider.processJwtTokenLoad(response, userDetails);

        return ResponseEntity.ok(ResponseMessage.of(true, "Token Refreshed"));
    }
}
