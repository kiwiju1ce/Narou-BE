package ssafy.narou.pjt.article.repository.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssafy.narou.pjt.article.entity.Article;

public interface ArticleRepositoryCustom {

    //제목, 닉네임 -> 기본 검색
    //인원, n박, 지역 -> 필터 검색
    Page<Article> searchArticlesByOption(Pageable page, String title, String nickname, Integer peopleNumber,
                                         Integer tripDay, String location, boolean orderBy);
}
