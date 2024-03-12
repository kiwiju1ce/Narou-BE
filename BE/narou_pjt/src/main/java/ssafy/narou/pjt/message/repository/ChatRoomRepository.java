package ssafy.narou.pjt.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.socket.WebSocketSession;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;
import ssafy.narou.pjt.message.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
    Optional<ChatRoom> findByRoomName(String roomName);
}
