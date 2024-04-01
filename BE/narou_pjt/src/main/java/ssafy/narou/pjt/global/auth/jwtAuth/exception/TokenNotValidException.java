package ssafy.narou.pjt.global.auth.jwtAuth.exception;

import ssafy.narou.pjt.global.error.NarouRuntimeException;

public class TokenNotValidException extends NarouRuntimeException{

    public TokenNotValidException(String message) {
        super(message);
    }

    public TokenNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotValidException(Throwable cause) {
        super(cause);
    }
}
