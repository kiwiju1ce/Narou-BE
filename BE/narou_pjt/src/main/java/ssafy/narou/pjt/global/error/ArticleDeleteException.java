package ssafy.narou.pjt.global.error;

import org.springframework.http.HttpStatus;

public class ArticleDeleteException extends RuntimeException{
    public ArticleDeleteException(String message) {super(message);
    }
}
