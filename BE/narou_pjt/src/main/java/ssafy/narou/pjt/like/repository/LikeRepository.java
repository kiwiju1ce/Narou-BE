package ssafy.narou.pjt.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.like.entity.Like;
import ssafy.narou.pjt.member.entity.Member;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
    Optional<Like> findByMemberAndArticle(Member member, Article article);

//    Optional<Integer> countByArticle(Article article);
}
