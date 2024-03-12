package ssafy.narou.pjt.global.auth.passwordAuth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.narou.pjt.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "passwordlogin")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PasswordLoginUser {

    @Id
    @GeneratedValue
    Long passwordLoginId;

    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member passwordLoginUser;

    @Column(nullable = false)
    private final String password;

    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime modifiedDate;

    @Builder
    public PasswordLoginUser(Member passwordLoginUser, String password, LocalDateTime modifiedDate) {
        this.passwordLoginUser = passwordLoginUser;
        this.password = password;
        this.modifiedDate = modifiedDate;
    }

    public static PasswordLoginUser of(Member member, String password){
        return PasswordLoginUser.builder()
                .passwordLoginUser(member)
                .password(password)
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
