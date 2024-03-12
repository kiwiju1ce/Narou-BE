package ssafy.narou.pjt.message.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.message.entity.ChatRoom;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class ChatRoomResponse {

    private Long chatRoomId;
    private String chatRoomName;
    private SimpleUserProfile profile;      // 상대방 프로필
    private ChatMessageResponse message;
    private LocalDateTime latest_date;      // 유저가 마지막으로 확인한 시간
}
