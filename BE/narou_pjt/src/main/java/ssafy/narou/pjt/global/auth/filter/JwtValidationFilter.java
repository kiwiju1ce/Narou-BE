package ssafy.narou.pjt.global.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ssafy.narou.pjt.global.auth.jwtAuth.JwtManager;
import ssafy.narou.pjt.global.auth.jwtAuth.TokenType;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.AccessTokenExpiredException;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.RefreshTokenExpiredException;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;

import java.io.IOException;

import static ssafy.narou.pjt.global.properties.JwtConstants.JWT_HEADER_NAME;
import static ssafy.narou.pjt.global.properties.JwtConstants.JWT_PREFIX;

@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAuthorizationToken(request);

        if (token != null) {
            try {
                jwtManager.validate(token);
            } catch (ExpiredJwtException e) {
                TokenType tokenType = jwtManager.getRawTokenType(token);
                switch (tokenType) {
                    case ACCESS_TOKEN -> throw new AccessTokenExpiredException("액세스 토큰이 만료되었습니다.");
                    case REFRESH_TOKEN -> throw new RefreshTokenExpiredException("리프레시 토큰이 만료되었습니다.");
                    default -> throw new TokenNotValidException("유효하지 않은 액세스 토큰입니다.");
                }
            } catch (IllegalArgumentException e) {
                throw new TokenNotValidException("유효하지 않은 액세스 토큰입니다.");
            }

            Authentication authentication = jwtManager.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        String authorization = request.getHeader(JWT_HEADER_NAME);
        if (authorization == null || authorization.length() <= JWT_HEADER_NAME.length()){
            return null;
        }
        return authorization.substring(JWT_PREFIX.length());
    }
}