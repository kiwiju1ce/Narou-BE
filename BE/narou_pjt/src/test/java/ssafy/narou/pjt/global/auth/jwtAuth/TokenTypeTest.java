package ssafy.narou.pjt.global.auth.jwtAuth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ssafy.narou.pjt.global.auth.jwtAuth.exception.TokenNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenTypeTest {

    @Test
    void convertTokenTypeTest() {
        String type1 = "access-token";
        String type2 = "invalid-token";

        assertEquals(TokenType.ACCESS_TOKEN, TokenType.convertTokenType(type1));
        assertThrows(TokenNotValidException.class, ()->TokenType.convertTokenType(type2));
    }
}