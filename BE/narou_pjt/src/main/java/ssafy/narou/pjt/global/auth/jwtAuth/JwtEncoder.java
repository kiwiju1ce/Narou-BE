package ssafy.narou.pjt.global.auth.jwtAuth;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.SIG.*;
import static ssafy.narou.pjt.global.properties.JwtConstants.*;

public class JwtEncoder {

    private final SecretKey key;

    public JwtEncoder(SecretKey key) {
        this.key = key;
    }

    public String createNewToken(NarouUserDetails userDetails, TokenType tokenType){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenType.getExpiration());
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .header().type(JWT_TOKEN_TYPE)
                .and()
                .claim(JWT_TOKEN_TYPE, tokenType.getName())
                .issuer(JWT_ISSUER)
                .subject(String.valueOf(userDetails.getUserId()))
                .claim(JWT_AUTHORITIES, authorities)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(key, HS256)
                .compact();
    }
}
