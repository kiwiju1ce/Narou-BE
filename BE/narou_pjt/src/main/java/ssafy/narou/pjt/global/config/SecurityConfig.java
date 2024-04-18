package ssafy.narou.pjt.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ssafy.narou.pjt.global.auth.filter.JwtValidationFilter;
import ssafy.narou.pjt.global.auth.filter.TokenBasedUsernamePasswordAuthenticationFilter;
import ssafy.narou.pjt.global.auth.handler.LoginAuthenticationFailureHandler;
import ssafy.narou.pjt.global.auth.handler.LoginAuthenticationSuccessHandler;
import ssafy.narou.pjt.global.auth.handler.NarouAccessDeniedHandler;
import ssafy.narou.pjt.global.auth.handler.NarouAuthenticationEntryPoint;
import ssafy.narou.pjt.global.auth.jwtAuth.JwtManager;
import ssafy.narou.pjt.global.auth.oauthAuth.service.NarouOAuth2AuthorizedClientService;
import ssafy.narou.pjt.global.auth.oauthAuth.service.NarouOAuth2UserInfoService;
import ssafy.narou.pjt.global.auth.passwordAuth.service.NarouPasswordAuthorizedUserService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    private final NarouPasswordAuthorizedUserService narouPasswordAuthorizedUserService;
    private final NarouOAuth2UserInfoService narouUserInfoService;
    private final NarouAccessDeniedHandler narouAccessDeniedHandler;
    private final NarouOAuth2AuthorizedClientService narouOAuth2AuthorizedClientService;
    private final JwtManager jwtManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("https://narou-test.duckdns.org"));
                    config.setAllowedMethods(Arrays.asList(
                                                    HttpMethod.GET.name(),
                                                    HttpMethod.POST.name(),
                                                    HttpMethod.PUT.name(),
                                                    HttpMethod.DELETE.name(),
                                                    HttpMethod.PATCH.name(),
                                                    HttpMethod.OPTIONS.name()
                    ));
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)  // jwt 사용하면 disable, session 사용하면 설정할 것.
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers(HttpMethod.POST, "/api/picture/upload").permitAll() // 권한 설정 uri -> 팔로우 등 기능에 적용할 것.
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(narouAccessDeniedHandler)
                )
                .userDetailsService(narouPasswordAuthorizedUserService)     //
                .formLogin(auth -> auth
                        .loginPage("https://narou-test.duckdns.org/login")
                        .loginProcessingUrl("/api/users/account/login")
                        .successHandler(loginAuthenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler())      // 지워도 상관 없을듯

                )
                .oauth2Login(auth -> auth
                                .loginPage("https://narou-test.duckdns.org/login")
                                .redirectionEndpoint(endpoint->endpoint
                                        .baseUri("/users/login/oauth2/code/*")
                                )
                                .userInfoEndpoint(endpoint -> endpoint
                                        .userService(narouUserInfoService)
                                )
                                .authorizedClientService(narouOAuth2AuthorizedClientService)
                                .successHandler(loginAuthenticationSuccessHandler)
                                .failureHandler(loginAuthenticationFailureHandler)      // 지워도 상관 없을듯
                )
                .addFilterBefore(new JwtValidationFilter(jwtManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler("https://narou-test.duckdns.org/login");
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new NarouAuthenticationEntryPoint();
    }

}