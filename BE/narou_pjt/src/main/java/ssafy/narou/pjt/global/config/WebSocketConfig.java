package ssafy.narou.pjt.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.*;
import ssafy.narou.pjt.message.controller.NarouChannelInterceptor;

import static org.springframework.messaging.simp.SimpMessageType.*;


@Configuration
//@EnableWebSocketSecurity
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final NarouChannelInterceptor narouChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/narou").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * SimpleBroker는 /topic, /queue destination 헤더 메시지를 라우팅함
     * 이 때, /topic은 게시글 알람, /queue는 1대1 채팅방에 사용
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/api/topic", "/api/queue");    // @MessageMapping으로 받을 요청
        registry.setApplicationDestinationPrefixes("/api/app");      // message broker를 활용해 메시지 보내기
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(narouChannelInterceptor);
    }

//    @Bean
//    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages
////                .simpDestMatchers("/**").permitAll()
////                .simpSubscribeDestMatchers("/pub/**").authenticated()
//                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll();
//        return messages.build();
//    }
}