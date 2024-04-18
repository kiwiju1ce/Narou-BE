package ssafy.narou.pjt.global.auth.jwtAuth;

import io.jsonwebtoken.ExpiredJwtException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtManagerTest {

    @Autowired JwtManager jwtManager;
    private static NarouUserDetails userDetails;

    @BeforeAll
    static void beforeAll() {
        userDetails = NarouUserDetails.builder()
                .userId(10L)
                .authorities(Set.of(new SimpleGrantedAuthority("authority1"),
                                    new SimpleGrantedAuthority("authority2")))
                .build();
    }

    @Test
    void AccessToken_getAuthentication() {
        String access = jwtManager.createJWT(userDetails, TokenType.ACCESS_TOKEN);

        JwtAuthenticationToken authentication = jwtManager.getAuthentication(access);

        assertEquals(userDetails, authentication.getPrincipal());
        assertThat(authentication.getCredentials()).isInstanceOf(JwtAccessToken.class);
    }

    @Test
    void RefreshToken_getAuthentication() {
        String refresh = jwtManager.createJWT(userDetails, TokenType.REFRESH_TOKEN);

        JwtAuthenticationToken authentication = jwtManager.getAuthentication(refresh);

        assertEquals(userDetails, authentication.getPrincipal());
        assertThat(authentication.getCredentials()).isInstanceOf(JwtRefreshToken.class);
    }

    @Test
    void validate() {
        String expired = "eyJ0eXAiOiJ0b2tlbi10eXBlIiwiYWxnIjoiSFMyNTYifQ.eyJ0b2tlbi10eXBlIjoiYWNjZXNzLXRva2VuIiwiaXNzIjoiTmFyb3UiLCJzdWIiOiIxMCIsImF1dGhvcml0aWVzIjoiYXV0aG9yaXR5MixhdXRob3JpdHkxIiwiaWF0IjoxNzEyMTUxOTY4LCJleHAiOjE3MTIxNTE5Njh9.Ff4eR4nQvPY0iKHbj1fUj9s-XW7aKP2f7xnmY6U0UwE";
        String invalid = "invalid-token";

        assertThrows(ExpiredJwtException.class, () -> jwtManager.validate(expired));
        assertThrows(IllegalArgumentException.class, () -> jwtManager.validate(invalid));
    }

    @Test
    void getRawTokenType() {
        String expired = "eyJ0eXAiOiJ0b2tlbi10eXBlIiwiYWxnIjoiSFMyNTYifQ.eyJ0b2tlbi10eXBlIjoiYWNjZXNzLXRva2VuIiwiaXNzIjoiTmFyb3UiLCJzdWIiOiIxMCIsImF1dGhvcml0aWVzIjoiYXV0aG9yaXR5MixhdXRob3JpdHkxIiwiaWF0IjoxNzEyMTUxOTY4LCJleHAiOjE3MTIxNTE5Njh9.Ff4eR4nQvPY0iKHbj1fUj9s-XW7aKP2f7xnmY6U0UwE";

        TokenType tokenType = jwtManager.getRawTokenType(expired);

        assertEquals(tokenType, TokenType.ACCESS_TOKEN);
    }
}