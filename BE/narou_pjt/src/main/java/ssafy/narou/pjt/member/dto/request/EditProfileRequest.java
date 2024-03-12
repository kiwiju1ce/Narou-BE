package ssafy.narou.pjt.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.member.entity.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileRequest {
    private Long user_id;
    private String nickname;
    private String introduction;

    public Member toEntity(String filePath){
        return Member.builder()
                .userId(user_id)
                .nickname(nickname)
                .profileImage(filePath)
                .introduction(introduction)
                .build();
    }
}
