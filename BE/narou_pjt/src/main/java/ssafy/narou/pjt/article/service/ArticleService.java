package ssafy.narou.pjt.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.article.dto.request.ArticleRequest;
import ssafy.narou.pjt.article.dto.response.ArticleDetailResponse;
import ssafy.narou.pjt.article.dto.response.FeedResponse;
import ssafy.narou.pjt.article.dto.response.HashTagResponse;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.ArticleToTag;
import ssafy.narou.pjt.article.entity.Feed;
import ssafy.narou.pjt.article.repository.article.ArticleRepository;
import ssafy.narou.pjt.article.repository.feed.FeedRepository;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.error.ArticleDeleteException;
import ssafy.narou.pjt.global.utils.AuthenticationUtils;
import ssafy.narou.pjt.global.utils.S3FileUploadService;
import ssafy.narou.pjt.like.repository.LikeRepository;
import ssafy.narou.pjt.like.service.LikeService;
import ssafy.narou.pjt.scrap.service.ScrapService;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FeedRepository feedRepository;
    private final ArticleHashTagService articleHashTagService;
    private final S3FileUploadService s3FileUploadService;
    private final ScrapService scrapService;
    private final LikeService likeService;

    public Long save(ArticleRequest newArticleRequest, MultipartFile thumbnail, List<MultipartFile> files) {
        String thumbnailPath = s3FileUploadService.saveImageFile(thumbnail);

        Article savedArticle = articleRepository.save(newArticleRequest.toEntity(thumbnailPath));
        articleHashTagService.saveHashTag(savedArticle, newArticleRequest.getTagNames());

        List<Feed> list = new ArrayList<>();
        Iterator<MultipartFile> iterator = files.iterator();
        newArticleRequest.getFeeds().forEach(feed -> {
            String filePath = s3FileUploadService.saveImageFile(iterator.next());
            list.add(feed.toEntity(savedArticle.getId(), filePath));
        });

        feedRepository.saveAll(list);
        return savedArticle.getId();
    }

    public ArticleDetailResponse findArticle(Long articleId){
        Article targetArticle = articleRepository.findById(articleId).orElseThrow(RuntimeException::new);
        log.info("++++++++++++++Article++++++++++++++++++" + targetArticle);
        List<FeedResponse> feeds = feedRepository.findByarticleId(articleId);
        List<FeedResponse> listedFeeds = feeds.stream()
                .sorted(Comparator.comparing(FeedResponse::getNthDay))
                .sorted(Comparator.comparing(FeedResponse::getSequence))
                .toList();

        log.info("================feeds============" + feeds);
        List<ArticleToTag> articleToTags = articleHashTagService.findHashTagListByArticle(targetArticle);
        List<HashTagResponse> hashtags = new ArrayList<>();
        for (ArticleToTag articleToTag : articleToTags) {
            hashtags.add(new HashTagResponse(articleToTag.getHashtag().getId(), articleToTag.getHashtag().getTagName()));
        }
        ArticleDetailResponse articleDetailResponse = ArticleDetailResponse.of(targetArticle, listedFeeds, hashtags);

        Optional<NarouUserDetails> authentication = AuthenticationUtils.getAuthentication();

        if(authentication.isPresent()){
            Long loginUserId = authentication.get().getUserId();
            log.info("checkScraped is null : {}", scrapService.checkScraped(loginUserId,targetArticle.getId()).isEmpty());
            scrapService.checkScraped(loginUserId,targetArticle.getId()).ifPresentOrElse(
                    info -> articleDetailResponse.setScraped(true),
                    () -> articleDetailResponse.setScraped(false));
            log.info("checkLiked is null : {}", likeService.checkLiked(loginUserId,targetArticle.getId()).isEmpty());
            likeService.checkLiked(loginUserId,targetArticle.getId()).ifPresentOrElse(
                    info -> articleDetailResponse.setLiked(true),
                    () -> articleDetailResponse.setLiked(false));
            log.info("response setLiked : {}", articleDetailResponse.getLiked());
            log.info("response setScraped : {}", articleDetailResponse.getScraped());
        } else {
            articleDetailResponse.setLiked(false);
            articleDetailResponse.setScraped(false);
        }

        viewCount(targetArticle);
        return articleDetailResponse;
    }

    //게시물 검색 리스트
    public Page<Article> getArticleList(Pageable page, String title, String nickname, Integer tripDay, Integer peopleNumber, String location, boolean orderBy) {

        return articleRepository.searchArticlesByOption(page, title, nickname, tripDay, peopleNumber, location, orderBy);

    }
    
    //조회수 증가
    private void viewCount(Article article) {
        articleRepository.updateViewCount(article.getId());

    }

    //게시글 삭제
    public void deleteArticle(Long userId, Long articleId) {
        Article article =articleRepository.findById(articleId).orElseThrow(RuntimeException::new);
        if(!Objects.equals(article.getMember().getUserId(), userId)){
            throw new ArticleDeleteException("삭제 권한이 없습니다.");
        }
        articleRepository.deleteById(articleId);
    }
}
