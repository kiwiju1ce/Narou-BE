package ssafy.narou.pjt.message.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.BaseEntity;
import ssafy.narou.pjt.member.entity.Member;

@Entity
@Table(name = "chat_message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;
}
