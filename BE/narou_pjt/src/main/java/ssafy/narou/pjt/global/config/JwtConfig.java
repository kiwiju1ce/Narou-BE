package ssafy.narou.pjt.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ssafy.narou.pjt.global.auth.jwtAuth.JwtManager;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final ObjectMapper objectMapper;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtManager jwtManager(){
        return new JwtManager(secretKey, objectMapper);
    }
}
