package ssafy.narou.pjt.member.repository;

import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;

import java.util.Optional;

public interface MemberRepositoryCustom {
//    Optional<Member> findByEmail(String email);
//    Optional<Member> findByNickname(String nickname);
    Optional<SimpleUserProfile> findProfileById(Long userId);
    Optional<SimpleUserProfile> findProfileByIdLoginUser(Long searchedUserId, Long userId);
    Optional<SimpleUserProfile> findProfileByEmail(String email);
    void updateUserProfile(Member profile);
    void updateRefreshToken(String name, String refreshToken);

}