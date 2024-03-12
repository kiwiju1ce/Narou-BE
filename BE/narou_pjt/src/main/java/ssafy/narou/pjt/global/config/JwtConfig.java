package ssafy.narou.pjt.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ssafy.narou.pjt.global.auth.jwt.JwtTokenProvider;
import ssafy.narou.pjt.global.properties.TokenProperties;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        return new JwtTokenProvider(secretKey);
    }
}
