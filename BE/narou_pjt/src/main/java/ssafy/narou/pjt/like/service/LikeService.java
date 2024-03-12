package ssafy.narou.pjt.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.dto.response.ArticleDetailResponse;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.repository.article.ArticleRepository;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.utils.AuthenticationUtils;
import ssafy.narou.pjt.like.entity.Like;
import ssafy.narou.pjt.like.repository.LikeRepository;
import ssafy.narou.pjt.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    
    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;
    public void addLike(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Member member = Member.builder().userId(userId).build();
        //중복 좋아요 방지
        if (isNotAlreadyLike(member, article)) {
            likeRepository.save(new Like(member, article));
            articleRepository.updateLikeCount(articleId);
        }
    }
    //저장 취소
    public void cancelLike(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Member member = Member.builder().userId(userId).build();
        Like like = likeRepository.findByMemberAndArticle(member, article).orElseThrow();
        likeRepository.delete(like);
        articleRepository.cancelLikeCount(articleId);
    }

    public Optional<Like> checkLiked(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();

            Member member = Member.builder().userId(userId).build();

            return likeRepository.findByMemberAndArticle(member, article);

    }

    private boolean isNotAlreadyLike(Member member, Article article) {
        return likeRepository.findByMemberAndArticle(member, article).isEmpty();
    }
    
    
}
