package ssafy.narou.pjt.message.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;
import ssafy.narou.pjt.message.entity.ChatRoom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomResponse> findAllChatRoomById(Long userId) {
        return null;
    }
}
