package ssafy.narou.pjt.global.auth.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

@Slf4j
public class JwtUtils {
    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request){
        String authorization = request.getHeader(JWT_HEADER);
        if (authorization == null || authorization.length() <= JWT_HEADER.length()){
            log.info("JWT AccessToken is null");
            return null;
        }

        return authorization.substring(JWT_PREFIX.length());
    }

    public static void tokenLoad(HttpServletResponse response, String name, String token){
//        Cookie cookie = new Cookie(name, token);
//        ResponseCookie useridCookie = ResponseCookie.from(name, token)
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .build();
//        response.addHeader("Set-Cookie", useridCookie.toString());
//        response.addCookie(cookie);
        response.addHeader(name, token);
        log.info("{} : {}", name, token);
    }

}
