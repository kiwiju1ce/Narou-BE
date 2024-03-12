package ssafy.narou.pjt.article.repository.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.narou.pjt.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom{
    Page<Article> findAll(Pageable pageable);
//    @EntityGraph(attributePaths = {"feeds", "member"})
//    Article findWithAllById(Long id);

    @Modifying
    @Query("update Article a set a.viewCount = a.viewCount + 1 where a.id = :id")
    void updateViewCount(@Param(value = "id") Long id);

    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount + 1 where a.id = :id")
    void updateLikeCount(@Param(value = "id") Long id);

    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount - 1 where a.id = :id")
    void cancelLikeCount(@Param(value = "id") Long id);

}
