package ssafy.narou.pjt.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt.expiration")
public class TokenProperties {

    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}
