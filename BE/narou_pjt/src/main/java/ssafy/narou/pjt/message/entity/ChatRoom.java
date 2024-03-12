package ssafy.narou.pjt.message.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;
import ssafy.narou.pjt.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false)
    private String roomName;

    @Builder
    public ChatRoom(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}