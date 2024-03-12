package ssafy.narou.pjt.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import ssafy.narou.pjt.member.entity.Member;

@Getter
@Builder
@AllArgsConstructor
public class SimpleUserProfile {
    private Long userId;
    private String nickname;
    private String profileImage;
    private String introduction;
    private Integer followers;
    private Integer followings;
    @Setter
    private Boolean isFollowing;

    @QueryProjection
    public SimpleUserProfile(Long userId, String nickname, String profileImage, String introduction) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduction = introduction;
    }

    @QueryProjection
    public SimpleUserProfile(Long userId, String nickname, String profileImage, String introduction, Integer followers, Integer followings) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.followers = followers;
        this.followings = followings;
    }

    public static SimpleUserProfile toDto(Member member){
        return SimpleUserProfile.builder()
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .profileImage(member.getNickname())
                .introduction(member.getIntroduction())
                .followers(member.getFollowers())
                .followings(member.getFollowings())
                .build();
    }
}
