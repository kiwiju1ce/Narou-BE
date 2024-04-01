package ssafy.narou.pjt.global.auth.jwtAuth.exception;

import ssafy.narou.pjt.global.error.NarouRuntimeException;

public class AccessTokenExpiredException extends NarouRuntimeException {
    public AccessTokenExpiredException(String message) {
        super(message);
    }

    public AccessTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenExpiredException(Throwable cause) {
        super(cause);
    }
}
