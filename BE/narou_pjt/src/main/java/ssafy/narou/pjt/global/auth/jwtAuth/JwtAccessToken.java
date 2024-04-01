package ssafy.narou.pjt.global.auth.jwtAuth;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JwtAccessToken extends JwtAbstractToken {

    private final boolean almostExpired;

    protected JwtAccessToken(String tokenValue, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        super(tokenValue, issuedAt, expiresAt);
        this.almostExpired = determineExpiration();
    }

    private boolean determineExpiration() {
        return getExpiresAt().minusMinutes(5)
                .isAfter(LocalDateTime.now());
    }
}
