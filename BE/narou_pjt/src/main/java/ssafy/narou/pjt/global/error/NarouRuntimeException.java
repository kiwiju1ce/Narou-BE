package ssafy.narou.pjt.global.error;

public class NarouRuntimeException extends RuntimeException{
    public NarouRuntimeException(String message) {
        super(message);
    }

    public NarouRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NarouRuntimeException(Throwable cause) {
        super(cause);
    }
}
