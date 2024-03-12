package ssafy.narou.pjt.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.utils.AuthenticationUtils;
import ssafy.narou.pjt.global.utils.S3FileUploadService;
import ssafy.narou.pjt.global.validation.NarouUserId;
import ssafy.narou.pjt.member.dto.request.EditProfileRequest;
import ssafy.narou.pjt.member.dto.request.FollowRequest;
import ssafy.narou.pjt.member.dto.response.FollowListResponse;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.error.NicknameDuplicationException;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.service.MemberService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private static final String FOLLOWER_LIST_TYPE_NAME = "follower_list";
    private static final String FOLLOWING_LIST_TYPE_NAME = "following_list";

    private final MemberService memberService;
    private final S3FileUploadService fileUploadService;
    private final ObjectMapper objectMapper;

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long user_id){
        Assert.notNull(user_id, "userId should not be null to get UserProfile");
        SimpleUserProfile profile = memberService.getUserProfile(user_id);
        return ResponseEntity.ok(ResponseMessage.of(true, profile, "Success"));
    }

    @PatchMapping(value = "/profile/edit")
    public ResponseEntity<?> editUserProfile(@NarouUserId Long userId,
                                             @RequestPart("profile_data") String data,
                                             @RequestPart("profile_image") MultipartFile profile_image) throws JsonProcessingException {
        EditProfileRequest editProfileRequest = objectMapper.readValue(data, EditProfileRequest.class);
        if (editProfileRequest.getUser_id().equals(userId)){
            String filePath = fileUploadService.saveImageFile(profile_image);

            Member member = editProfileRequest.toEntity(filePath);
            memberService.updateUserProfile(member);
            return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
        }
        return ResponseEntity.badRequest().body(ResponseMessage.of(false, "잘못된 접근입니다."));
    }

//    @PatchMapping(value = "/profile/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> editUserProfile(@NarouUserId Long userId,
//                                             @RequestParam("user_id") Long user_id,
//                                             @RequestParam("nickname") String nickname,
//                                             @RequestParam("introduction") String introduction,
//                                             @RequestParam("profile_image") MultipartFile profile_image){
//        String filePath = fileUploadService.saveImageFile(profile_image);
//
////        Member member = editProfileRequest.toEntity(filePath);
//        Member member = Member.builder().userId(user_id).nickname(nickname).profileImage(filePath).introduction(introduction).build();
//        memberService.updateUserProfile(member);
//
//        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
//    }

    @PostMapping("/profile/dupcheck/{nickname}")
    public ResponseEntity<?> nicknameDupCheck(@PathVariable String nickname){
        Assert.notNull(nickname, "nickname should not be null to check duplication");

        if (memberService.isNicknameDuplicated(nickname)){
            throw new NicknameDuplicationException("이미 존재하는 닉네임입니다.");
        }
        return ResponseEntity.ok(ResponseMessage.of(true, "사용 가능한 닉네임입니다."));
    }


    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@NarouUserId Long userId, @RequestBody FollowRequest followRequest){
        if (followRequest.getFollowed_id().equals(userId)){         // 팔로우 팔로워 동일 인물
            return ResponseEntity.badRequest().body(ResponseMessage.of(false, "잘못된 접근입니다."));
        }
        memberService.followUser(followRequest.getFollowed_id(), userId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@NarouUserId Long userId, @RequestBody FollowRequest followRequest){
        if (followRequest.getFollowed_id().equals(userId)){         // 언팔로우 언팔로워 동일 인물
            return ResponseEntity.badRequest().body(ResponseMessage.of(false, "잘못된 접근입니다."));
        }
        memberService.unfollowUser(followRequest.getFollowed_id(), userId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

    @GetMapping("/profile/read/follower/list/{followedId}")
    public ResponseEntity<?> getFollowerList(@PathVariable Long followedId){
        List<SimpleUserProfile> follwerList = memberService.getFollowerList(followedId);
        FollowListResponse followerListResponse = FollowListResponse.builder()
                .type(FOLLOWER_LIST_TYPE_NAME)
                .userProfileList(follwerList)
                .build();

        return ResponseEntity.ok(ResponseMessage.of(true, followerListResponse, "Success"));
    }

    @GetMapping("/profile/read/following/list/{followerId}")
    public ResponseEntity<?> getFollowingList(@PathVariable Long followerId){
        List<SimpleUserProfile> follwerList = memberService.getFollowingList(followerId);
        FollowListResponse followingListResponse = FollowListResponse.builder()
                .type(FOLLOWING_LIST_TYPE_NAME)
                .userProfileList(follwerList)
                .build();

        return ResponseEntity.ok(ResponseMessage.of(true, followingListResponse, "Success"));
    }
}
