package ssafy.narou.pjt.global.auth.jwtAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ssafy.narou.pjt.global.auth.jwtAuth.JwtAuthenticationToken.authenticated;
import static ssafy.narou.pjt.global.auth.jwtAuth.TokenType.convertTokenType;
import static ssafy.narou.pjt.global.properties.JwtConstants.*;
import static ssafy.narou.pjt.global.utils.AuthenticationUtils.getAuthorities;
import static ssafy.narou.pjt.global.utils.StringUtil.safeString;
import static ssafy.narou.pjt.global.utils.TimeUtils.convertLDT;

@Slf4j
public class JwtManager {
    private final JwtParser jwtParser;
    private final JwtEncoder jwtEncoder;
    private final ObjectMapper objectMapper;

    public JwtManager(String secretKey, ObjectMapper objectMapper) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().requireIssuer(JWT_ISSUER).verifyWith(key).build();
        this.jwtEncoder = new JwtEncoder(key);
        this.objectMapper = objectMapper;
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

    public void validate(final String token) throws ExpiredJwtException, IllegalArgumentException {
        try {
            jwtParser.parseSignedClaims(token);
        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException(e);
        }
    }
    /** Expired token의 토큰 타입을 추출하기 위한 특수 메서드.
     *  반드시 올바른 토큰 형태라는 것을 검증한 후 사용할 것.
     */
    public TokenType getRawTokenType(final String token) {
        String[] split = token.split("\\.");
        byte[] decode = Base64.getDecoder().decode(split[1]);

        try {
            String type = parseTokenType(new String(decode));
            return convertTokenType(type);
        } catch (JsonProcessingException e) {
            throw new TokenNotValidException("토큰의 구성 정보가 잘못되었습니다.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private String parseTokenType(String payload) throws JsonProcessingException {
        Map<String, String> claims = objectMapper.readValue(payload, Map.class);
        return claims.get(JWT_TOKEN_TYPE);
    }

    public String createJWT(NarouUserDetails userDetails, TokenType tokenType) {
        return jwtEncoder.createNewToken(userDetails, tokenType);
    }

    private Claims getClaims(final String token) throws ExpiredJwtException, IllegalArgumentException {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    private Jwt extractCredential(final String token, final Claims payload) {
        final Date issuedAt = payload.getIssuedAt();
        final Date expiredAt = payload.getExpiration();
        final String type = safeString(payload.get(JWT_TOKEN_TYPE));

        Assert.notNull(issuedAt, "issuedAt should not be null in Token");
        Assert.notNull(expiredAt, "expiredAt should not be null in Token");
        return switch (type) {
            case JWT_ACCESS_TOKEN -> new JwtAccessToken(token, convertLDT(issuedAt), convertLDT(expiredAt));
            case JWT_REFRESH_TOKEN -> new JwtRefreshToken(token, convertLDT(issuedAt), convertLDT(expiredAt));
            default -> throw new TokenNotValidException("알 수 없는 토큰 타입");
        };
    }

    private NarouUserDetails extractPrincipal(final Claims payload) {
        final Long userId = Long.parseLong(payload.getSubject());
        final String parsed = safeString(payload.get(JWT_AUTHORITIES));
        final Set<GrantedAuthority> authorities = getAuthorities(parsed);

        Assert.notNull(userId, "userId should not be null in Token");
        Assert.notNull(authorities, "authorities should not be null in Token");
        return NarouUserDetails.builder()
                .userId(userId)
                .authorities(authorities)
                .build();
    }
}
