package ssafy.narou.pjt.global.auth.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import ssafy.narou.pjt.global.validation.Password;

@Getter
public class SignUpRequest {

    @Email private String email;
    @Password private String password;
    private String nickname;
}
