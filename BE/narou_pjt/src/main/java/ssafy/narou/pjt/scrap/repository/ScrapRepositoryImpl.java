package ssafy.narou.pjt.scrap.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.dto.response.ArticleSearchResponse;
import ssafy.narou.pjt.member.entity.Member;

import java.util.List;

import static ssafy.narou.pjt.article.entity.QArticle.article;
import static ssafy.narou.pjt.member.entity.QMember.member;
import static ssafy.narou.pjt.scrap.entity.QScrap.scrap;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class ScrapRepositoryImpl implements ScrapRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<ArticleSearchResponse> findScrapedArticle(Member targetMember, Pageable pageable) {
        QueryResults<ArticleSearchResponse> articleSearchQueryResults = queryFactory
                .select(Projections.constructor(
                        ArticleSearchResponse.class,
                        article.id,
                        article.title,
                        article.likeCount,
                        article.thumbnailPath,
                        article.startDate,
                        article.endDate,
                        article.route,
                        article.viewCount,
//                        member.userId,
//                        member.nickname,
                        article.member.userId,
                        article.member.nickname,
                        article.peopleNumber))
                .from(scrap)
                .leftJoin(scrap.article, article )
//                .leftJoin(scrap.member, member)
                .leftJoin(article.member, member)
//                .where(member.userId.eq(targetMember.getUserId()))
                .where(scrap.member.userId.eq(targetMember.getUserId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(scrap.createdTime.desc())
                .fetchResults();

        List<ArticleSearchResponse> contents = articleSearchQueryResults.getResults();

        long total = articleSearchQueryResults.getTotal();

        return new PageImpl<>(contents, pageable, total);
    }
}
