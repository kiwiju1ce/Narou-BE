package ssafy.narou.pjt.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.message.entity.ChatRoom;
import ssafy.narou.pjt.message.entity.MemberToChatRoom;

public interface MemberChatRoomRepository  extends JpaRepository<MemberToChatRoom, Long>, MemberChatRoomRepositoryCustom {
}
