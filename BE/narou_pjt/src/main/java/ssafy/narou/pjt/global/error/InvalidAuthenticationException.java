package ssafy.narou.pjt.global.error;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
