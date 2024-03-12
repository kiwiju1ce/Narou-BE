package ssafy.narou.pjt.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.Feed;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewFeedRequest {

    private Integer nthDay;
    private Integer sequence;
    private String content;
    private Double latitude;
    private Double longitude;
    private String address;
    private String location;
    private MultipartFile file;
    private String fileType;

    public Feed toEntity(Long articleId, String filePath){
        Article article = Article.builder().id(articleId).build();

        return Feed.builder()
                .article(article)
                .nthDay(nthDay)
                .sequence(sequence)
                .content(content)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .location(location)
                .filePath(filePath)
                .fileType(fileType)
                .build();
    }
}
