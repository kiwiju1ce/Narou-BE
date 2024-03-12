package ssafy.narou.pjt.scrap.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.scrap.entity.Scrap;

import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
//
//    Optional<Integer> countByArticle(Article article);
    Optional<Scrap> findByMemberAndArticle(Member member, Article article);
}
