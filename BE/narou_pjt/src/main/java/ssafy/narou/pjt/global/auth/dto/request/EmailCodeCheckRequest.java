package ssafy.narou.pjt.global.auth.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class EmailCodeCheckRequest {
    @Email private String email;
}
