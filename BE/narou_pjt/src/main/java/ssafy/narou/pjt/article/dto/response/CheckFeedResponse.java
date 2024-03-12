package ssafy.narou.pjt.article.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckFeedResponse {
    @NotEmpty
    private Long articleId;
    @NotEmpty
    private Integer nthDay;
    @NotEmpty
    private Integer order;
    private String content;
    private Double latitude;
    private Double longitude;
    private String address;
    private String location;
    private String filePath;
    private String fileType;

    @Builder
    public CheckFeedResponse(Long articleId, Integer nthDay, Integer order, String content, Double latitude, Double longitude, String address, String location, String filePath, String fileType) {
        this.articleId = articleId;
        this.nthDay = nthDay;
        this.order = order;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.location = location;
        this.filePath = filePath;
        this.fileType = fileType;
    }
}
