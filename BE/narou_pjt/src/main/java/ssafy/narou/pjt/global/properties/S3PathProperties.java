package ssafy.narou.pjt.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3PathProperties {

    private String bucket;
    private String baseUrl;
}
