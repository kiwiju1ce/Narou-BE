package ssafy.narou.pjt.message.controller;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import ssafy.narou.pjt.message.service.ChatService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NarouChannelInterceptor implements ChannelInterceptor {

    private static final String USER_ID_HEADER = "user-id";
    private static final String PATH_URL = "/queue/chat/";

    private final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        log.info("message : {}", command);

        if (StompCommand.CONNECT.equals(command)) {


        } else if (StompCommand.SEND.equals(command)) {


        } else if (StompCommand.DISCONNECT.equals(command)) {
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            Assert.notNull(sessionAttributes, "sessionAttributes should not be null");

            String roomName = parseRoomNamefromPath(accessor);
            Long userId = (Long) sessionAttributes.get(USER_ID_HEADER);

            chatService.updateChatRoomInfo(roomName, userId);
        }

        return message;
    }

    private String parseRoomNamefromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        return destination.substring(PATH_URL.length());
    }
}