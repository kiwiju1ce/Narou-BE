package ssafy.narou.pjt.global.auth.jwtAuth;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class JwtAbstractToken implements Jwt {

    private final String tokenValue;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;

    protected JwtAbstractToken(String tokenValue, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.tokenValue = tokenValue;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
