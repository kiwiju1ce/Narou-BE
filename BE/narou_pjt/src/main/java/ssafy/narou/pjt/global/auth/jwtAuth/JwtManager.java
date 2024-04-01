package ssafy.narou.pjt.global.auth.jwtAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;
import static ssafy.narou.pjt.global.auth.jwtAuth.JwtAuthenticationToken.authenticated;
import static ssafy.narou.pjt.global.properties.JwtConstants.*;
import static ssafy.narou.pjt.global.utils.AuthenticationUtils.getAuthorities;
import static ssafy.narou.pjt.global.utils.StringUtil.safeString;
import static ssafy.narou.pjt.global.utils.TimeUtils.convertLDT;

public class JwtManager {
    private final JwtParser jwtParser;
    private final JwtEncoder jwtEncoder;

    public JwtManager(String secretKey) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().requireIssuer(JWT_ISSUER).verifyWith(key).build();
        this.jwtEncoder = new JwtEncoder(key);
    }

    public JwtAuthenticationToken getAuthentication(final String token) {
        final Claims payload;
        try {
            payload = getClaims(token);
        } catch (Exception e) {
            throw new RuntimeException("뭔가 잘못되었습니다..", e);
        }

        final NarouUserDetails userDetails = extractPrincipal(payload);
        final Jwt jwtToken = extractCredential(token, payload);
        return authenticated(userDetails, jwtToken, userDetails.getAuthorities());
    }

    private Jwt extractCredential(final String token, final Claims payload) {
        final Date issuedAt = payload.getIssuedAt();
        final Date expiredAt = payload.getExpiration();
        final String type = safeString(payload.get(JWT_TOKEN_TYPE));

        Assert.notNull(issuedAt, "issuedAt should not be null in Token");
        Assert.notNull(expiredAt, "expiredAt should not be null in Token");
        return switch (type) {
            case ACCESS_TOKEN -> new JwtAccessToken(token, convertLDT(issuedAt), convertLDT(expiredAt));
            case REFRESH_TOKEN -> new JwtRefreshToken(token, convertLDT(issuedAt), convertLDT(expiredAt));
            default -> throw new TokenNotValidException("알 수 없는 토큰 타입");
        };
    }

    private NarouUserDetails extractPrincipal(final Claims payload) {
        final Long userId = Long.parseLong(payload.getId());
        final String parsed = safeString(payload.get(JWT_AUTHORITIES));
        final Set<GrantedAuthority> authorities = getAuthorities(parsed);

        Assert.notNull(userId, "userId should not be null in Token");
        Assert.notNull(authorities, "authorities should not be null in Token");
        return NarouUserDetails.builder()
                .userId(userId)
                .authorities(authorities)
                .build();
    }

    public void validate(final String token) throws ExpiredJwtException, IllegalArgumentException {
        jwtParser.parseSignedClaims(token);
    }

    private Claims getClaims(final String token) throws ExpiredJwtException, IllegalArgumentException {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public TokenType getRawTokenType(final String token) {
        return TokenType.REFRESH_TOKEN;
    }

    public String createJWT(NarouUserDetails userDetails, TokenType tokenType) {
        return jwtEncoder.createNewToken(userDetails, tokenType);
    }
}
