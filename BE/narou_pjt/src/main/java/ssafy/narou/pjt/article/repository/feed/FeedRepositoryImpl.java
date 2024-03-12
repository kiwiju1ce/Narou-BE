package ssafy.narou.pjt.article.repository.feed;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.article.dto.response.FeedResponse;

import java.util.List;

import static ssafy.narou.pjt.article.entity.QFeed.feed;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<FeedResponse> findByarticleId(Long articleId) {

        return queryFactory.select(Projections.constructor
                (FeedResponse.class, feed.nthDay, feed.sequence, feed.content, feed.latitude, feed.longitude,
                        feed.address, feed.location, feed.filePath, feed.fileType))
                .from(feed)
                .where(feed.article.id.eq(articleId))
                .fetch();
    }
}
