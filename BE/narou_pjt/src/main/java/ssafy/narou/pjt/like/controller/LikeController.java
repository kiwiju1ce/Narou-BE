package ssafy.narou.pjt.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.validation.NarouUserId;
import ssafy.narou.pjt.like.service.LikeService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/article")
@RestController
public class LikeController {

    private final LikeService likeService;

    //좋아요 등록
    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> likeArticle(@NarouUserId Long userId, @PathVariable Long articleId) {
        likeService.addLike(userId, articleId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

    //좋아요 취소
    @PostMapping("/cancellike/{articleId}")
    public ResponseEntity<?> cancelLike(@NarouUserId Long userId, @PathVariable Long articleId) {
        likeService.cancelLike(userId, articleId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

}