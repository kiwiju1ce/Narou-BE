package ssafy.narou.pjt.comment.dto.response;

import java.time.LocalDateTime;
import ssafy.narou.pjt.comment.entity.Comment;
import lombok.*;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Integer deleted;

    private String nickname;
    private String profileImage;
    private Long userId;

    @Builder
    public CommentResponseDto(Comment comment, String nickname, String profileImage) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.deleted=comment.getDeleted();
        this.createdTime=comment.getCreatedTime();
        this.updatedTime=comment.getUpdatedTime();
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.userId = comment.getMember().getUserId();
    }
}
