package ssafy.narou.pjt.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ssafy.narou.pjt.global.auth.dto.response.JwtResponse;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.auth.jwt.JwtTokenProvider;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.repository.MemberRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository
            = new HttpSessionOAuth2AuthorizationRequestRepository();
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private static final String DEFAULT_REDIRECT_URL = "https://narou-test.duckdns.org";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAttributes(request, response);
        NarouUserDetails userDetails = (NarouUserDetails) authentication.getPrincipal();
        String[] tokens = jwtTokenProvider.processJwtTokenLoad(response, userDetails);

        String email = userDetails.getName();

        Optional<SimpleUserProfile> profile = memberRepository.findProfileByEmail(email);

        if (profile.isPresent()) {
//            SimpleUserProfile profile1 = profile.get();
//            Cookie userid = new Cookie("userid", String.valueOf(profile1.getUserId()));
//            Cookie nickname = new Cookie("nickname", profile1.getNickname());
//            Cookie profileimage = new Cookie("profileimage", profile1.getProfileImage());
//            ResponseCookie useridCookie = ResponseCookie.from("userid", String.valueOf(profile1.getUserId()))
//                    .path("/")
//                    .secure(true)
//                    .sameSite("None")
//                    .build();
//            ResponseCookie nicknameCookie = ResponseCookie.from("nickname", URLEncoder.encode(profile1.getNickname(), StandardCharsets.UTF_8))
//                    .path("/")
//                    .secure(true)
//                    .sameSite("None")
//                    .build();
//            ResponseCookie profileimageCookie = ResponseCookie.from("profileimage", profile1.getProfileImage())
//                    .path("/")
//                    .secure(true)
//                    .sameSite("None")
//                    .build();
//            response.addHeader("Set-Cookie", useridCookie.toString());
//            response.addHeader("Set-Cookie", nicknameCookie.toString());
//            response.addHeader("Set-Cookie", profileimageCookie.toString());
//            response.setHeader("Access-Control-Allow-Origin", "https://narou-test.duckdns.org");

//            log.info("userid : {}", profile1.getUserId());
//            log.info("nickname : {}", profile1.getNickname());
//            log.info("profileimage : {}", profile1.getProfileImage());
//            log.info("response : {}", response.getHeaderNames());
//            log.info("request : {}", request.getHeaderNames());
//            log.info("request : {}", request.getHeader("Origin"));
            JwtResponse jwtResponse = JwtResponse.builder()
                    .AccessToken(tokens[0])
                    .RefreshToken(tokens[1])
                    .simpleUserProfile(profile.get())
                    .build();

            String jsonProfile = objectMapper.writeValueAsString(jwtResponse);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(jsonProfile);
        }

    }

    private void clearAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequest(request, response);
    }

}