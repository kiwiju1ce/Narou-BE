package ssafy.narou.pjt.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowListResponse {

    private String type;
    private List<SimpleUserProfile> userProfileList;
}
