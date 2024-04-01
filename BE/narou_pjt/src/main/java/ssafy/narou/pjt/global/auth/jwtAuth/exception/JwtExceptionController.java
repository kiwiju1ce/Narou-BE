package ssafy.narou.pjt.global.auth.jwtAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;

@RestControllerAdvice
public class JwtExceptionController {

    @ExceptionHandler(AccessTokenExpiredException.class)
    public ResponseEntity<?> emailDuplicationException(AccessTokenExpiredException atee){
        return new ResponseEntity<>(ResponseMessage.of(false, atee.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<?> emailDuplicationException(RefreshTokenExpiredException rtee){
        return new ResponseEntity<>(ResponseMessage.of(false, rtee.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
