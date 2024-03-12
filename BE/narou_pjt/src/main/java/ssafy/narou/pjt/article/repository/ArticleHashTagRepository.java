package ssafy.narou.pjt.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.ArticleToTag;

import java.util.List;

public interface ArticleHashTagRepository extends JpaRepository<ArticleToTag, Long> {
    List<ArticleToTag> findAllByArticle(Article article);

    List<ArticleToTag> findAllByHashtagTagName(String tagName);
}
