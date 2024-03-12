package ssafy.narou.pjt.message.repository;

import ssafy.narou.pjt.message.dto.response.ChatMessageResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepositoryCustom {
    List<ChatMessageResponse> findChatRoomMessageList(String chatRoomName, Long userId, LocalDateTime separator);
}
