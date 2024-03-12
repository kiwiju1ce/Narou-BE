package ssafy.narou.pjt.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.comment.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByArticleIdOrderByCreatedTimeDesc(Long articleId);

    @Query("SELECT c, m.nickname, m.profileImage FROM Comment c JOIN c.member m WHERE c.article.id = :articleId ORDER BY c.createdTime DESC")
    List<Object[]> findCommentsWithMemberInfoByArticleId(@Param("articleId") Long articleId);


}