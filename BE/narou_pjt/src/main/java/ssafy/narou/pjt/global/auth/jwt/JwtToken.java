package ssafy.narou.pjt.global.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Assert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.error.InvalidAuthenticationException;
import ssafy.narou.pjt.global.error.TokenNotValidException;

import javax.crypto.SecretKey;
import java.util.*;

@Slf4j
public class JwtToken {

    private static final String TOKEN_TYPE = "JWT";
    private static final String JWT_ISSUER = "Narou";
//    private final String AUTHORITIES_KEY = "Authority";
    @Getter private final String token;

    private final SecretKey key;

    public JwtToken(NarouUserDetails userDetails, SecretKey key, long expiration) {
        Assert.notNull(userDetails, "userDetails should not be null to create JWT");
        Assert.notNull(key, "key should not be null to create JWT");

        this.key = key;
        this.token = createNewToken(userDetails, expiration);
    }

    public JwtToken(String token, SecretKey key) {
        Assert.notNull(token, "token should not be null");
        Assert.notNull(key, "key should not be null to parse JWT");
        this.key = key;
        this.token = token;
    }

    /**
     *
     * @param userDetails
     * @return AccessToken String
     * iss : Narou
     * sub : 유저 email
     * iat : 현재
     * exp : 현재 + Token expiration
     * claim : authorities ("로그인 타입 명시") -> 의미 없어서 삭제함
     */
    public String createNewToken(NarouUserDetails userDetails, long tokenExpirationTime){

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenExpirationTime);

        return Jwts.builder()
                    .header().type(TOKEN_TYPE)
                .and()
                    .claim("id", userDetails.getUserId())
                    .issuer(JWT_ISSUER)
                    .subject(userDetails.getName())    // Email로 설정
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .signWith(key)
                    .compact();
    }

    public Claims getTokenClaims(){
        try {
            return Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token) // 인증 처리
                    .getPayload();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new InvalidAuthenticationException("만료된 액세스 토큰입니다.");
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new TokenNotValidException("유효하지 않은 액세스 토큰입니다.");
        }
        return null;
    }

    public boolean isTokenNonExpired(){
        Claims claims = getTokenClaims();
        return claims.getExpiration() != null
                && new Date().before(claims.getExpiration());
    }
}
