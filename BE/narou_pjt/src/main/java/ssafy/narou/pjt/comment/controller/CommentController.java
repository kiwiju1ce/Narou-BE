package ssafy.narou.pjt.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.narou.pjt.comment.dto.request.CommentRequestDto;
import ssafy.narou.pjt.comment.dto.response.CommentResponseDto;
import ssafy.narou.pjt.comment.service.CommentService;
import ssafy.narou.pjt.global.validation.NarouUserId;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write/{articleId}")
    public ResponseEntity<?> commentCreate (@PathVariable Long articleId, @NarouUserId Long userId, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(articleId, userId, commentRequestDto);
    }

    @PutMapping(value = "/modify/{commentId}", consumes = "application/json")
    public ResponseEntity<?> commentUpdate(@PathVariable Long commentId, @NarouUserId Long userId, @Valid @RequestBody CommentRequestDto commentRequestDto){
        return commentService.updateComment(commentId, userId, commentRequestDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable Long commentId, @NarouUserId Long userId) {
        return commentService.deleteComment(commentId, userId);
    }

    @GetMapping("/read/{articleId}")
    public List<CommentResponseDto> commentRead(@PathVariable Long articleId){
        return commentService.readComment(articleId);
    }

}
