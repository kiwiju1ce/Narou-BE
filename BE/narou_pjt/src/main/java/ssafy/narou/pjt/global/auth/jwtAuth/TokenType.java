package ssafy.narou.pjt.global.auth.jwtAuth;

import lombok.Getter;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;

import java.util.Arrays;

@Getter
public enum TokenType {

    ACCESS_TOKEN("access-token", 100000000),
    REFRESH_TOKEN("refresh-token", 1000000000);

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
