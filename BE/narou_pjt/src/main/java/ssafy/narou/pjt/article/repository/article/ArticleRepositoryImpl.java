package ssafy.narou.pjt.article.repository.article;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.QArticle;

import java.util.List;
import java.util.Optional;
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression titleContains(String title) {

        return Optional.ofNullable(title)
                .filter(t -> !t.isEmpty())
                .map(QArticle.article.title::containsIgnoreCase)
                .orElse(null);
    }

    private BooleanExpression nicknameContains(String nickname) {
        return Optional.ofNullable(nickname)
                .filter(t -> !t.isEmpty())
                .map(QArticle.article.member.nickname::containsIgnoreCase)
                .orElse(null);
    }

    private BooleanExpression peopleNumberEquals(Integer peopleNumber) {
        return Optional.ofNullable(peopleNumber)
                .filter(num -> num > 0)
                .map(QArticle.article.peopleNumber::eq)
                .orElse(null);
    }

    private BooleanExpression locationContains(String location) {
        return Optional.ofNullable(location)
                .filter(t -> !t.isEmpty())
                .map(QArticle.article.route::containsIgnoreCase)
                .orElse(null);
    }

    private BooleanExpression tripdayEquals(Integer tripDay) {

        NumberTemplate<Integer> dateDiff = Expressions.numberTemplate(Integer.class,
                "DATEDIFF({0}, {1})", QArticle.article.endDate, QArticle.article.startDate);

        return Optional.ofNullable(tripDay)
                .map(dateDiff::eq)
                .orElse(null);
    }

    @Override
    public Page<Article> searchArticlesByOption(Pageable page, String title, String nickname, Integer tripDay, Integer peopleNumber, String location, boolean orderBy) {
        QArticle article = QArticle.article;
        BooleanExpression predicate = article.id.gt(0);

//        Optional.ofNullable(titleContains(title)).ifPresent(predicate::and);
//        Optional.ofNullable(nicknameContains(nickname)).ifPresent(predicate::and);
//        Optional.ofNullable(peopleNumberEquals(peopleNumber)).ifPresent(predicate::and);
//        Optional.ofNullable(locationContains(location)).ifPresent(predicate::and);
//        Optional.ofNullable(tripdayEquals(tripDay)).ifPresent(predicate::and);
        predicate = predicate.and(titleContains(title));
        predicate = predicate.and(nicknameContains(nickname));
        predicate = predicate.and(peopleNumberEquals(peopleNumber));
        predicate = predicate.and(locationContains(location));
        predicate = predicate.and(tripdayEquals(tripDay));
        log.info("final predicate: {}", predicate);
        List<Article> articles;
        if (orderBy) {
             articles = queryFactory.selectFrom(article)
                    .where(predicate)
                    .orderBy(article.likeCount.desc())
                    .fetch();

        } else {
            articles = queryFactory.selectFrom(article)
                    .where(predicate)
                    .orderBy(article.id.desc())
                    .fetch();
        }

        int start = (int) page.getOffset();
        int end = Math.min((start + page.getPageSize()), articles.size());

        List<Article> articleList = articles.subList(start, end);
        return new PageImpl<>(articleList, page, articles.size());

    }


}
