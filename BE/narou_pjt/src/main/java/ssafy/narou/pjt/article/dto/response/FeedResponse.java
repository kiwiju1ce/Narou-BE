package ssafy.narou.pjt.article.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.entity.Feed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponse {

    private Integer nthDay;
    private Integer sequence;
    private String content;
    private Double latitude;
    private Double longitude;
    private String address;
    private String location;
    private String filePath;
    private String fileType;


    @Builder
    public FeedResponse(Integer nthDay, Integer sequence, String content, Double latitude, Double longitude, String address, String location, String filePath, String fileType) {
        this.nthDay = nthDay;
        this.sequence = sequence;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.location = location;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public Feed toEntity(Long articleId) {
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
