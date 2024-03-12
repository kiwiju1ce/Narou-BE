package ssafy.narou.pjt.scrap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.repository.article.ArticleRepository;
import ssafy.narou.pjt.like.entity.Like;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.scrap.entity.Scrap;
import ssafy.narou.pjt.scrap.repository.ScrapRepository;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ArticleRepository articleRepository;

    public void addScrap(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Member member = Member.builder().userId(userId).build();
        //중복 스크랩 방지
        if (isNotAlreadyScrap(member, article)) {
            scrapRepository.save(new Scrap(member, article));
        }
    }

    //사용자가 이미 저장한 게시물인지 체크
    private boolean isNotAlreadyScrap(Member member, Article article) {
        return scrapRepository.findByMemberAndArticle(member, article).isEmpty();
    }

    //저장 취소
    public void cancelScrap(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Member member = Member.builder().userId(userId).build();
        Scrap scrap = scrapRepository.findByMemberAndArticle(member, article).orElseThrow();
        scrapRepository.delete(scrap);
    }

    public Optional<Scrap> checkScraped(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Member member = Member.builder().userId(userId).build();

        return scrapRepository.findByMemberAndArticle(member, article);

    }
}