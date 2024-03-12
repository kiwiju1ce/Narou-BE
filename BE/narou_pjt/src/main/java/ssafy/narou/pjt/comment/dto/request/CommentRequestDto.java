package ssafy.narou.pjt.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
public class CommentRequestDto {

//    @NotNull
//    private Integer deleted;

    @NotBlank
    private String content;
}
