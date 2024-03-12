package ssafy.narou.pjt.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.utils.AuthenticationUtils;
import ssafy.narou.pjt.global.utils.S3FileUploadService;
import ssafy.narou.pjt.member.dto.request.EditProfileRequest;
import ssafy.narou.pjt.member.entity.Follow;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.global.error.NoSuchMemberException;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.repository.FollowInfoRepository;
import ssafy.narou.pjt.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FollowInfoRepository followInfoRepository;
    private final S3FileUploadService fileUploadService;

    @Override
    public boolean emailDupCheck(String email) {
        Optional<Member> member = memberRepository.findMemberByEmail(email);
        return member.isPresent();
    }

    @Override
    public boolean isNicknameDuplicated(String nickname) {
        Optional<Member> member = memberRepository.findMemberByNickname(nickname);
        return member.isPresent();
    }

    @Override
    public SimpleUserProfile getUserProfile(Long userId) {
        Optional<SimpleUserProfile> found = memberRepository.findProfileById(userId);
        SimpleUserProfile profile = found.orElseThrow(() -> new NoSuchMemberException("해당 유저가 존재하지 않습니다."));

        Optional<NarouUserDetails> authentication = AuthenticationUtils.getAuthentication();

        if (authentication.isPresent()){
            Long loginUserId = authentication.get().getUserId();

            followInfoRepository.loadFollow(userId, loginUserId)
                    .ifPresentOrElse(info -> profile.setIsFollowing(info.getActivated()),
                                      () -> profile.setIsFollowing(false));
        }else {
            profile.setIsFollowing(false);
        }

        return profile;
    }

    @Override
    @Transactional
//    public void updateUserProfile(EditProfileRequest profile) {
//        String filePath = fileUploadService.saveImageFile(profile.getProfile_image());
//        Member member = profile.toEntity(filePath);
    public void updateUserProfile(Member member) {
        memberRepository.updateUserProfile(member);
    }

    @Override
    @Transactional
    public void followUser(Long followedId, Long followingId) {
        Optional<Follow> followInfo = followInfoRepository.loadFollow(followedId, followingId);

        if (followInfo.isPresent()){
            followInfoRepository.updateFollow(followedId, followingId, true);
        }else {
            Follow follow = Follow.of(followedId, followingId);
            followInfoRepository.save(follow);
        }

//        followInfo.map(Follow::getActivated)
//                .filter(activated -> !activated)
//                .ifPresentOrElse((inactive) -> followInfoRepository.updateFollow(followedId, followingId, !inactive),
//                                    () -> followInfoRepository.createFollow(followedId, followingId));
        memberRepository.hitFollwersCount(followedId, 1);
        memberRepository.hitFollwingCount(followingId, 1);
    }

    @Override
    @Transactional
    public void unfollowUser(Long followedId, Long followingId) {
        log.info("unfollow : {}", followedId);
        followInfoRepository.updateFollow(followedId, followingId, false);
        memberRepository.hitunFollwersCount(followedId, 1);
        memberRepository.hitunFollwingCount(followingId, 1);
    }

    @Override
    public List<SimpleUserProfile> getFollowerList(Long userId) {
        return followInfoRepository.findFollowerById(userId);
    }

    @Override
    public List<SimpleUserProfile> getFollowingList(Long userId) {
        return followInfoRepository.findFollowById(userId);
    }
}
