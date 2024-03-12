package ssafy.narou.pjt.message.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.simp.SimpMessageType;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.message.entity.ChatMessage;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class ChatMessageResponse {

    private SimpleUserProfile profile;
    private String chatRoomName;
    private Long messageId;
    private SimpMessageType messageType;
    private String content;
    private LocalDateTime latest_time;

    @QueryProjection
    public ChatMessageResponse(SimpleUserProfile profile, String chatRoomName, Long messageId, String content, LocalDateTime latest_time) {
        this.profile = profile;
        this.chatRoomName = chatRoomName;
        this.messageId = messageId;
        this.content = content;
        this.latest_time = latest_time;
    }

    public static ChatMessageResponse toDto(ChatMessage message){
        return ChatMessageResponse.builder()
                .profile(SimpleUserProfile.toDto(message.getMember()))
                .chatRoomName(message.getChatRoom().getRoomName())
                .messageId(message.getMessageId())
                .messageType(SimpMessageType.MESSAGE)
                .content(message.getContent())
                .latest_time(message.getCreatedTime())
                .build();
    }
}
