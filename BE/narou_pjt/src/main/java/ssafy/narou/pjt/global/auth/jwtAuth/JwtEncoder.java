package ssafy.narou.pjt.global.auth.jwtAuth;

import io.jsonwebtoken.Jwts;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

import static ssafy.narou.pjt.global.properties.JwtConstants.JWT_ISSUER;
import static ssafy.narou.pjt.global.properties.JwtConstants.JWT_TOKEN_TYPE;

public class JwtEncoder {

    private final SecretKey key;

    public JwtEncoder(SecretKey key) {
        this.key = key;
    }

    public String createNewToken(NarouUserDetails userDetails, TokenType tokenType){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenType.getExpiration());

        return Jwts.builder()
                .header().type(JWT_TOKEN_TYPE)
                .and()
                .claim(JWT_TOKEN_TYPE, tokenType.getName())
                .issuer(JWT_ISSUER)
                .subject(String.valueOf(userDetails.getUserId()))
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }
}
