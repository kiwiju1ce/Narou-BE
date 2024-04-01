package ssafy.narou.pjt.global.auth.jwtAuth;

import java.time.LocalDateTime;

public interface Jwt {
    String getTokenValue();
    LocalDateTime getIssuedAt();
    LocalDateTime getExpiresAt();
}
