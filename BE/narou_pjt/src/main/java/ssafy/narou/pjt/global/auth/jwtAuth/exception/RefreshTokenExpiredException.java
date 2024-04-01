package ssafy.narou.pjt.global.auth.jwtAuth.exception;

import ssafy.narou.pjt.global.error.NarouRuntimeException;

public class RefreshTokenExpiredException extends NarouRuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpiredException(Throwable cause) {
        super(cause);
    }
}
