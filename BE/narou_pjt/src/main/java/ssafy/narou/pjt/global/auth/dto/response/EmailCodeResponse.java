package ssafy.narou.pjt.global.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailCodeResponse {

    String email;
    String code;
    LocalDateTime created_time;

    @Builder
    public EmailCodeResponse(String email, String code, LocalDateTime created_time) {
        this.email = email;
        this.code = code;
        this.created_time = created_time;
    }
}
