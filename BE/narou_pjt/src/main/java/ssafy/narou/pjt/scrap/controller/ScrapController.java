package ssafy.narou.pjt.scrap.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.narou.pjt.article.dto.response.ArticleSearchResponse;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.validation.NarouUserId;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.scrap.repository.ScrapRepositoryImpl;
import ssafy.narou.pjt.scrap.service.ScrapService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/article")
@RestController
public class ScrapController {

    private final ScrapRepositoryImpl scrapRepositoryImpl;
    private final ScrapService scrapService;

    //스크랩 등록
    @PostMapping("/scrap/{articleId}")
    public ResponseEntity<?> scrapArticle(@NarouUserId Long userId, @PathVariable Long articleId){
        scrapService.addScrap(userId, articleId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

    @PostMapping("/cancelscrap/{articleId}")
    public ResponseEntity<?> cancelScrap(@NarouUserId Long userId, @PathVariable Long articleId){
        scrapService.cancelScrap(userId, articleId);
        return ResponseEntity.ok(ResponseMessage.of(true, "Success"));
    }

    @GetMapping("/scrap/list/{userId}")
    public ResponseEntity<?> viewScrappedArticle(@PathVariable Long userId,
                                                 @PageableDefault(size = 9, value = 9, sort = "id", direction = Sort.Direction.DESC) Pageable page
                                                 ) {

            Member member = Member.builder().userId(userId).build();

            Page<ArticleSearchResponse> scrapedArticle = scrapRepositoryImpl.findScrapedArticle(member, page);

            return ResponseEntity.ok(ResponseMessage.of(true, scrapedArticle, "Success"));

    }
}
