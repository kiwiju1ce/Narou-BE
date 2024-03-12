package ssafy.narou.pjt.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "follow")
@NoArgsConstructor
public class Follow {

    @EmbeddedId
    FollowId followId;

    @MapsId(value = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id")
    private Member followedMember;

    @MapsId(value = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Member followingMember;

    private Boolean activated;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTime;

    @Builder
    public Follow(FollowId followId, Member followedMember, Member followingMember, Boolean activated, LocalDateTime createdTime) {
        this.followId = followId;
        this.followedMember = followedMember;
        this.followingMember = followingMember;
        this.activated = activated;
        this.createdTime = createdTime;
    }

    public static Follow of(Long followedId, Long followingId){
        Member follwed = Member.builder().userId(followedId).build();
        Member follwing = Member.builder().userId(followingId).build();

        return Follow.builder()
                    .followId(new FollowId(followedId, followingId))
                    .followedMember(follwed)
                    .followingMember(follwing)
                    .activated(true)
                    .createdTime(LocalDateTime.now())
                    .build();
    }
}
