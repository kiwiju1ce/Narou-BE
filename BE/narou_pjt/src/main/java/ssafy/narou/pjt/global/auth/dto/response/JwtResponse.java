package ssafy.narou.pjt.global.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;

@Getter
@Builder
@AllArgsConstructor
public class JwtResponse {

    private String AccessToken;
    private String RefreshToken;
    private SimpleUserProfile simpleUserProfile;
}
