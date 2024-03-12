package ssafy.narou.pjt.member.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.member.repository.MemberRepository;

import java.sql.Ref;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class RefreshToken {

    @Id
    @GeneratedValue
    Long refreshTokenId;

    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private final String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime created_time;

    @Builder
    public RefreshToken(Member member, String refreshToken, LocalDateTime created_time) {
        this.member = member;
        this.refreshToken = refreshToken;
        this.created_time = created_time;
    }

    public static RefreshToken of(Member member, String refreshToken){
        return RefreshToken.builder()
                .member(member)
                .refreshToken(refreshToken)
                .created_time(LocalDateTime.now())
                .build();
    }
}
