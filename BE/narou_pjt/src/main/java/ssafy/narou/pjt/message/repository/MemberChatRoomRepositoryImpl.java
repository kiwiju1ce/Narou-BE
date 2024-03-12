package ssafy.narou.pjt.message.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberChatRoomRepositoryImpl implements MemberChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomResponse> findAllChatRoomById(Long userId) {
        return null;
    }
}
