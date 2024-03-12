package ssafy.narou.pjt.message.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.member.dto.response.QSimpleUserProfile;
import ssafy.narou.pjt.message.dto.response.ChatMessageResponse;
import ssafy.narou.pjt.message.dto.response.QChatMessageResponse;

import java.time.LocalDateTime;
import java.util.List;

import static ssafy.narou.pjt.member.entity.QMember.member;
import static ssafy.narou.pjt.message.entity.QChatMessage.chatMessage;
import static ssafy.narou.pjt.message.entity.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatMessageResponse> findChatRoomMessageList(String chatRoomName, Long userId, LocalDateTime separator) {

        return queryFactory.select(new QChatMessageResponse(
                                            new QSimpleUserProfile(member.userId, member.nickname, member.profileImage, member.introduction),
                                            chatRoom.roomName,
                                            chatMessage.messageId,
                                            chatMessage.content,
                                            chatMessage.createdTime
                                    ))
                .from(chatMessage)
                .leftJoin(chatMessage.member, member).on(member.userId.eq(userId)).fetchJoin()
                .leftJoin(chatMessage.chatRoom, chatRoom).on(chatRoom.roomName.eq(chatRoomName)).fetchJoin()
//                .leftJoin(chatMessage.chatRoom, memberToChatRoom.chatRoom)
                .where(chatMessage.createdTime.before(separator)
//                        member.userId.eq(userId),
//                        chatRoom.roomName.eq(chatRoomName))
                )
                .orderBy(chatMessage.createdTime.asc())
                .limit(20)
                .fetch();
    }
}
