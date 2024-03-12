package ssafy.narou.pjt.member.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.global.auth.oauthAuth.model.OAuth2UserInfo;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private String introduction;

    private Integer followers;

    private Integer followings;

    private LocalDateTime joinDate;

    private LocalDateTime modifiedDate;

    @Builder
    public Member(Long userId, String email, String nickname, LoginType loginType, String profileImage, String introduction, Integer followers, Integer followings, LocalDateTime joinDate, LocalDateTime modifiedDate) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.loginType = loginType;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.followers = followers;
        this.followings = followings;
        this.joinDate = joinDate;
        this.modifiedDate = modifiedDate;
    }

    public static Member createPasswordUser(String email, String nickname){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .loginType(LoginType.PASSWORD_USER)
                .profileImage(null)
                .introduction(null)
                .followers(0)
                .followings(0)
                .joinDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    public static Member createOAuth2User(OAuth2UserInfo userInfo){
        return Member.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .loginType(LoginType.OAUTH2_USER)
                .profileImage(userInfo.getProfileImage())
                .introduction(null)
                .followers(0)
                .followings(0)
                .joinDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
