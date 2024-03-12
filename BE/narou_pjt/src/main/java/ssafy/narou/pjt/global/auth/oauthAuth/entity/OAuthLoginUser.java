package ssafy.narou.pjt.global.auth.oauthAuth.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth2login")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class OAuthLoginUser {

    @Id
    @GeneratedValue
    private Long socialLoginId;

    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member oAuthLoginUser;

    @Column(nullable = false)
    private final String accessToken;

    @Enumerated(EnumType.STRING)
    private final ProviderType provider;

    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime tokenUpdateTime;

    @Builder
    public OAuthLoginUser(Long socialLoginId, String accessToken, ProviderType provider, LocalDateTime tokenUpdateTime) {
        this.socialLoginId = socialLoginId;
        this.accessToken = accessToken;
        this.provider = provider;
        this.tokenUpdateTime = tokenUpdateTime;
    }
}
