package ssafy.narou.pjt.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class TokenBasedUsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private static final String LOGIN_HEADER = "Authorization";
    private static final String ACCESS_TOKEN_NAME = "Access-Token";
    private static final String REFRESH_TOKEN_NAME = "Refresh-Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request access-control-allow-origin header : {}", request.getHeader("access-control-allow-origin"));
        log.info("request URL : {}", request.getRequestURL());
        log.info("request DispatcherType : {}", request.getDispatcherType());
        log.info("request QueryString : {}", request.getQueryString());
        log.info("request URI : {}", request.getRequestURI());
        log.info("request content type : {}", request.getContentType());

        String requiredUri = request.getServletPath();
        if (pathMatcher.match("/auth/internal/*", requiredUri)){
            log.info("loading token ");
            String accessToken = request.getHeader(ACCESS_TOKEN_NAME);
            String refreshToken = request.getHeader(REFRESH_TOKEN_NAME);
        }

        filterChain.doFilter(request, response);
    }

    private String encodeBase64Token(HttpServletRequest request){
        String header = request.getHeader(LOGIN_HEADER);

        if (header == null) {
            return null;
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BASIC)) {
            throw new BadCredentialsException("Empty basic authentication token");
        }
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded = decode(base64Token);

        return new String(decoded, StandardCharsets.UTF_8);
    }

    public String getPassword(HttpServletRequest request){
        String decoded = encodeBase64Token(request);
        int delim = getDelimIdx(decoded);
        System.out.println(decoded);
        return decoded.substring(delim + 1);
    }

    public String getEmail(HttpServletRequest request){
        String decoded = encodeBase64Token(request);
        int delim = getDelimIdx(decoded);

        return decoded.substring(0, delim);
    }

    private int getDelimIdx(String token){
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return delim;
    }

    private byte[] decode(byte[] base64Token) {
        try {
            return Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException ex) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
    }
    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return getEmail(request);
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return getPassword(request);
    }

}
