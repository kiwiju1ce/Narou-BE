package ssafy.narou.pjt.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ssafy.narou.pjt.global.auth.jwt.JwtToken;
import ssafy.narou.pjt.global.auth.jwt.JwtTokenProvider;
import ssafy.narou.pjt.global.auth.jwt.JwtUtils;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = JwtUtils.getAccessToken(request);
        log.info("token value in header : {}", tokenValue);
        if (tokenValue != null) {
            JwtToken token = tokenProvider.convertJwtToken(tokenValue);

            if (token.isTokenNonExpired()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("jwtAuthenticationFilter loaded token on SecurityContext : {}", ((NarouUserDetails) authentication.getPrincipal()).getName());
            }
        }

        filterChain.doFilter(request, response);
    }
}