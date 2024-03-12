    package ssafy.narou.pjt.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String DEFAULT_REDIRECT_URI = "https://narou-test.duckdns.org/login";
    private static final AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository
            = new HttpSessionOAuth2AuthorizationRequestRepository();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("login failure : {}", exception.getMessage());
        log.info("login failure class : {}", exception.getClass());
        exception.printStackTrace();
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = authorizationRequestRepository.loadAuthorizationRequest(request);

        String redirectUrl = Optional.ofNullable(oAuth2AuthorizationRequest)
                .map(OAuth2AuthorizationRequest::getRedirectUri)
                .orElse(DEFAULT_REDIRECT_URI);

        authorizationRequestRepository.removeAuthorizationRequest(request, response);

        response.sendRedirect(redirectUrl);
    }
}
