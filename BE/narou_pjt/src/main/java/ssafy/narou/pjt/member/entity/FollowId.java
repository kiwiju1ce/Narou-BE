package ssafy.narou.pjt.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class FollowId implements Serializable {

    @Column(name = "followed_id")
    @EqualsAndHashCode.Include
    private Long followedId;

    @Column(name = "following_id")
    @EqualsAndHashCode.Include
    private Long followingId;
}
