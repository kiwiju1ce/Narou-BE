package ssafy.narou.pjt.message.repository;

import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;

import java.util.List;

public interface MemberChatRoomRepositoryCustom {
    List<ChatRoomResponse> findAllChatRoomById(Long userId);
}
