package ssafy.narou.pjt.global.auth.jwtAuth;

import lombok.Getter;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;

import java.util.Arrays;

@Getter
public enum TokenType {

    ACCESS_TOKEN("access-token", 86400L * 1 * 1000),
    REFRESH_TOKEN("refresh-token", 86400L * 90 * 1000),
    UNKNOWN_TOKEN("unknown-token", 0L);

    private final String name;
    private final long expiration;

    TokenType(String name, long expiration) {
        this.name = name;
        this.expiration = expiration;
    }

    public static TokenType convertTokenType(String type) {
        return Arrays.stream(TokenType.values())
                .filter(value -> value.name.equals(type))
                .findFirst()
                .orElseThrow(() -> new TokenNotValidException("알 수 없는 토큰 타입"));
    }
}
