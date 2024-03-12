package ssafy.narou.pjt.article.repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Feed;

@Transactional
public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom{

}

