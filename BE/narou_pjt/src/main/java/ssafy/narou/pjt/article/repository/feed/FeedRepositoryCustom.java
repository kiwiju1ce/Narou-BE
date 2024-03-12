package ssafy.narou.pjt.article.repository.feed;

import ssafy.narou.pjt.article.dto.response.FeedResponse;

import java.util.List;

public interface FeedRepositoryCustom{

    List<FeedResponse> findByarticleId(Long articleId);
}
