package ssafy.narou.pjt.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.ArticleToTag;
import ssafy.narou.pjt.article.entity.HashTag;
import ssafy.narou.pjt.article.repository.ArticleHashTagRepository;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ArticleHashTagService {

    private final HashTagService hashTagService;
    private final ArticleHashTagRepository articleHashTagRepository;
    public void saveHashTag(Article article, List<String> tagNames){

        if(tagNames.isEmpty()) return;

        tagNames.stream().map(hashtag ->
                hashTagService.findByTagName(hashtag)
                        .orElseGet(() -> hashTagService.save(hashtag)))
                .forEach(hashtag -> mapHashTagToArticle(article, hashtag));

    }

    private Long mapHashTagToArticle(Article article, HashTag hashtag) {
        return articleHashTagRepository.save(new ArticleToTag(article, hashtag)).getId();
    }

    public List<ArticleToTag> findHashTagListByArticle(Article article){
        return articleHashTagRepository.findAllByArticle(article);
    }

//    public Page<Article> findAllByHashtag(int page, String hashtag){
//        List<Sort.Order> sortsList = new ArrayList<>();
//        sortsList.add(Sort.Order.desc("created_time"));
//    }

}
