package ssafy.narou.pjt.global.auth.passwordAuth.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.codec.Hex;

import java.security.SecureRandom;

@SpringBootTest
class PasswordUserServiceImplTest {


    @Test
    public void passwordEncoding(){
        String password = "qwerty1234!";
        String salt = generateSalt();
    }

    private String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBytes = new byte[16];
        secureRandom.nextBytes(saltBytes);
        return Hex.encode(saltBytes).toString();
    }

}