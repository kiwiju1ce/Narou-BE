package ssafy.narou.pjt.message.dto.request;

import lombok.Getter;
import org.springframework.messaging.simp.SimpMessageType;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.message.entity.ChatMessage;
import ssafy.narou.pjt.message.entity.ChatRoom;
import ssafy.narou.pjt.message.entity.ContentType;

@Getter
public class ChatMessageRequest {

    private Long userId;
    private Long chatRoomId;
    private SimpMessageType messageType;
    private ContentType contentType;
    private String content;

    public ChatMessage toEntity(){
        Member member = Member.builder().userId(userId).build();
        ChatRoom chatRoom = ChatRoom.builder().roomId(chatRoomId).build();

        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(content)
                .contentType(contentType)
                .build();
    }
}
