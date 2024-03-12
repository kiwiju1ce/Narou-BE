package ssafy.narou.pjt.scrap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssafy.narou.pjt.article.dto.response.ArticleSearchResponse;
import ssafy.narou.pjt.member.entity.Member;

public interface ScrapRepositoryCustom {

    Page<ArticleSearchResponse> findScrapedArticle(Member targetMember, Pageable pageable);
}
