package ssafy.narou.pjt.member.service;

import ssafy.narou.pjt.member.dto.request.EditProfileRequest;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.entity.Member;

import java.util.List;

public interface MemberService {

    SimpleUserProfile getUserProfile(Long userId);
//    void updateUserProfile(EditProfileRequest profile);
    void updateUserProfile(Member member);
    boolean emailDupCheck(String email);
    boolean isNicknameDuplicated(String nickname);
    void followUser(Long followedId, Long followingId);
    void unfollowUser(Long followedId, Long followingId);
    List<SimpleUserProfile> getFollowerList(Long userId);
    List<SimpleUserProfile> getFollowingList(Long userId);
}
