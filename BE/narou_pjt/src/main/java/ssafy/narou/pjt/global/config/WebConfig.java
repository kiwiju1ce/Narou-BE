package ssafy.narou.pjt.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ssafy.narou.pjt.global.validation.AuthenticationNarouUserArgumentResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("https://narou-test.duckdns.org",
                        "https://kauth.kakao.com", "https://kapi.kakao.com",
                        "https://nid.naver.com", "https://openapi.naver.com",
                        "https://accounts.google.com", "https://oauth2.googleapis.com", "https://www.googleapis.com")  // TODO : 허용할 포트 설정할 것.
                .allowedMethods(
                    HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(),
                    HttpMethod.PATCH.name(),
                    HttpMethod.OPTIONS.name()
                )
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*");  // 프론트로 넘길 때 노출시킬 header 설정
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationNarouUserArgumentResolver());
    }

    @Bean
    public AuthenticationNarouUserArgumentResolver authenticationNarouUserArgumentResolver(){
        return new AuthenticationNarouUserArgumentResolver();
    }
}
