package ssafy.narou.pjt.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.article.dto.request.ArticleRequest;
import ssafy.narou.pjt.article.dto.response.ArticleDetailResponse;
import ssafy.narou.pjt.article.dto.response.ArticleSearchResponse;
import ssafy.narou.pjt.article.service.ArticleService;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.validation.NarouUserId;
import ssafy.narou.pjt.scrap.repository.ScrapRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ScrapRepository scrapRepository;
    private final ObjectMapper objectMapper;

    //게시글 상세보기
    @GetMapping("/read/{articleId}")
    public ResponseEntity<?> articleDetail(@PathVariable Long articleId) {
        ArticleDetailResponse article = articleService.findArticle(articleId);
        log.info("===================search log=======================" + article.toString());
        return ResponseEntity.ok(ResponseMessage.of(true, article, "Success"));
    }

    //게시글 작성
//    @PutMapping("/write")
//    public ResponseEntity<?> articleSave(@RequestBody NewArticleRequest newArticleRequest) {
//
//        articleService.save(newArticleRequest);
//        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
//    }

    @PutMapping("/write")
    public ResponseEntity<?> articleSave(
//            @NarouUserId Long userId,
            @RequestParam("article") String article,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("files") List<MultipartFile> files) throws JsonProcessingException {
        ArticleRequest articleRequest = objectMapper.readValue(article, ArticleRequest.class);
//        if (!Objects.equals(userId, articleRequest.getMemberId())) {
//            throw new ArticleWriteException("작성 권한이 없습니다.");
//        } else {
            Long articleId = articleService.save(articleRequest, thumbnail, files);
//        }
        return ResponseEntity.ok(ResponseMessage.of(true, articleId, "Success"));
    }

//    //게시글 수정
//    @PutMapping("/edit/{articleId}")
//    public ResponseEntity<?> articleEdit(@NarouUserId Long userId, @PathVariable Long articleId, @RequestBody NewArticleRequest newArticleRequest){
//
//    }

    //게시글 삭제
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> articleDelete(@NarouUserId Long userId, @PathVariable Long articleId) {
        articleService.deleteArticle(userId, articleId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Deleted"));
    }

    //게시글 비교
    @GetMapping("/compare")
    public ResponseEntity<?> compareArticle(@RequestParam Long value1, @RequestParam Long value2) {
        List<ArticleSearchResponse> responses = new ArrayList<>(2);

        ArticleSearchResponse article1 = ArticleSearchResponse.scrapedArticleToDto(scrapRepository.findById(value1).get());
        ArticleSearchResponse article2 = ArticleSearchResponse.scrapedArticleToDto(scrapRepository.findById(value2).get());
        responses.add(article1);
        responses.add(article2);

        return ResponseEntity.ok(ResponseMessage.of(true, responses, "Success"));
    }

    //게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchArticleByOption(
            @PageableDefault(value = 24) Pageable page,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) Integer tripDay,
            @RequestParam(required = false) Integer peopleNumber,
            @RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "false") boolean orderBy) {
        Page<ArticleSearchResponse> articleDtoList = ArticleSearchResponse.toDtoList(
                articleService.getArticleList(page, title, nickname, tripDay, peopleNumber, location, orderBy));
        log.info("page : {}", page.getPageSize());
        log.info("page : {}", page.getPageNumber());
        log.info("page : {}", page.getOffset());
        return ResponseEntity.ok(ResponseMessage.of(true, articleDtoList, "Success"));
    }


}
