package ssafy.narou.pjt.message.service;

import org.springframework.data.domain.Page;
import ssafy.narou.pjt.message.dto.request.ChatMessageRequest;
import ssafy.narou.pjt.message.dto.response.ChatMessageResponse;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;
import ssafy.narou.pjt.message.entity.ChatRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    ChatMessageResponse saveMessage(ChatMessageRequest chatMessageRequest);
    List<ChatMessageResponse> getMessageList(String chatRoomName, Long userId, LocalDateTime separator);
    ChatRoom createChatRoom(String chatRoomName);
    List<ChatRoomResponse> findAllChatRoom(Long userId);
    void updateChatRoomInfo(String roomName, Long userId);

}
