package ssafy.narou.pjt.global.auth.jwtAuth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtAccessTokenTest {

    @Test
    void isAlmostExpiredTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt1 = now.plusSeconds(2*60);
        LocalDateTime expiredAt2 = now.plusSeconds(5*60+1);

        JwtAccessToken test1 = new JwtAccessToken("test", now, expiredAt1);
        JwtAccessToken test2 = new JwtAccessToken("test", now, expiredAt2);

        assertTrue(test1.isAlmostExpired());
        assertFalse(test2.isAlmostExpired());
    }
}