package ssafy.narou.pjt.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.narou.pjt.message.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
}
