package ssafy.narou.pjt.member.repository;

import ssafy.narou.pjt.member.entity.Follow;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FollowInfoRepositoryCustom {
    Optional<Follow> loadFollow(Long followedId, Long followingId);
    List<SimpleUserProfile> findFollowerById(Long userId);
    List<SimpleUserProfile> findFollowById(Long userId);
//    void createFollow(Member followedId, Member followingId);
    void createFollow(Long followedId, Long followingId, boolean isActivated);
    void updateFollow(Long followedId, Long followingId, boolean isActivated);
}
