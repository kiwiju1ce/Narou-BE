package ssafy.narou.pjt.global.error.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.narou.pjt.global.error.NoSuchMemberException;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.error.*;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(EmailDuplicationException.class)
    public ResponseEntity<?> emailDuplicationException(EmailDuplicationException ede){
        return new ResponseEntity<>(ResponseMessage.of(false, ede.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<?> tokenNotValidException(TokenNotValidException tnve){
        return new ResponseEntity<>(ResponseMessage.of(false, tnve.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailServerException.class)
    public ResponseEntity<?> emailServerException(EmailServerException ese){
        return new ResponseEntity<>(ResponseMessage.of(false, ese.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailCodeNotMatchesException.class)
    public ResponseEntity<?> emailCodeNotMatchesException(EmailCodeNotMatchesException ecnme){
        return new ResponseEntity<>(ResponseMessage.of(false, ecnme.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NicknameDuplicationException.class)
    public ResponseEntity<?> nicknameDuplicationException(NicknameDuplicationException nde){
        return new ResponseEntity<>(ResponseMessage.of(false, nde.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<?> noSuchMemberException(NoSuchMemberException nsme){
        return new ResponseEntity<>(ResponseMessage.of(false, nsme.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException mane){
        return new ResponseEntity<>(ResponseMessage.of(false, "입력하신 정보가 형식에 맞지 않습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidAuthenticationException.class, AuthenticationException.class})
    public ResponseEntity<?> invalidAuthenticationException(AuthenticationException iae){
        return new ResponseEntity<>(ResponseMessage.of(false, "잘못된 권한 정보입니다."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ArticleDeleteException.class)
    public ResponseEntity<?> ArticleDeleteException(ArticleDeleteException iae){
        return new ResponseEntity<>(ResponseMessage.of(false, "삭제 권한이 없습니다."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<?> ImageConvertException(ImageUploadException ice){
        return new ResponseEntity<>(ResponseMessage.of(false, "이미지 업로드에 실패했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}