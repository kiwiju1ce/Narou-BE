package ssafy.narou.pjt.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.domain.Page;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.scrap.entity.Scrap;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ArticleSearchResponse {

    private Long id;
    private String title;
    private Integer likeCount;
    private String thumbnailPath;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private Long dateDiff;
    private List<LocationInfo> route;
    private Integer viewCount;
    private Long userId;
    private String nickname;
    private Integer peopleNumber;

    public ArticleSearchResponse(Long id, String title, Integer likeCount, String thumbnailPath, LocalDate startDate, LocalDate endDate, String route, Integer viewCount, Long userId, String nickname, Integer peopleNubmer) {
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.thumbnailPath = thumbnailPath;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateDiff =getDateDiff(startDate,endDate);
        this.route = getRouteList(route);
        this.viewCount = viewCount;
        this.userId = userId;
        this.nickname = nickname;
        this.peopleNumber = peopleNubmer;
    }

    public void setRoute(String route) {
        this.route = getRouteList(route);
    }

    private static List<LocationInfo> getRouteList(String route) {

        List<LocationInfo> locationInfos = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(route, "|");
        while (st.hasMoreTokens()) {
            locationInfos.add(new LocationInfo(
                    Integer.parseInt(st.nextToken().trim()),
                    Integer.parseInt(st.nextToken().trim()),
                    Double.parseDouble(st.nextToken().trim()),
                    Double.parseDouble(st.nextToken().trim()),
                    st.nextToken().trim(),
                    st.nextToken().trim()
                    ));
        }

        return locationInfos;
    }

    private static Long getDateDiff(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1L;
    }

    public static ArticleSearchResponse scrapedArticleToDto(Scrap scrap) {

    return ArticleSearchResponse.builder()
            .id(scrap.getId())
            .title(scrap.getArticle().getTitle())
            .likeCount(scrap.getArticle().getLikeCount())
            .thumbnailPath(scrap.getArticle().getThumbnailPath())
            .startDate(scrap.getArticle().getStartDate())
            .endDate(scrap.getArticle().getEndDate())
            .dateDiff(getDateDiff(scrap.getArticle().getStartDate(), scrap.getArticle().getEndDate()))
            .route(getRouteList(scrap.getArticle().getRoute()))
            .viewCount(scrap.getArticle().getViewCount())
            .userId(scrap.getMember().getUserId())
            .nickname(scrap.getMember().getNickname())
            .peopleNumber(scrap.getArticle().getPeopleNumber())
            .build();
    }

    public static Page<ArticleSearchResponse> toDtoList(Page<Article> articleList) {
        return articleList.map(m ->
                ArticleSearchResponse.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .likeCount(m.getLikeCount())
                        .thumbnailPath(m.getThumbnailPath())
                        .startDate(m.getStartDate())
                        .endDate(m.getEndDate())
                        .dateDiff(getDateDiff(m.getStartDate(), m.getEndDate()))
                        .route(getRouteList(m.getRoute()))
                        .viewCount(m.getViewCount())
                        .userId(m.getMember().getUserId())
                        .nickname(m.getMember().getNickname())
                        .peopleNumber(m.getPeopleNumber())
                        .build());
    }
}
