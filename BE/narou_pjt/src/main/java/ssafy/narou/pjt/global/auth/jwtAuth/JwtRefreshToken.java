package ssafy.narou.pjt.global.auth.jwtAuth;

import java.time.LocalDateTime;

public class JwtRefreshToken extends JwtAbstractToken {
    protected JwtRefreshToken(String tokenValue, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        super(tokenValue, issuedAt, expiresAt);
    }
}
