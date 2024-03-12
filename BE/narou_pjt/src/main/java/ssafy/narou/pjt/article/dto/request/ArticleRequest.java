package ssafy.narou.pjt.article.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

    private Long memberId;
    private String title;
    private List<String> tagNames;
    private List<NewFeedRequest> feeds;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer peopleNumber;

    public Article toEntity(String thumbnailPath) {
        Member member = Member.builder().userId(memberId).build();

        return  Article.builder()
                .member(member)
                .title(title)
                .likeCount(0)
                .route(routeToString())
                .thumbnailPath(thumbnailPath)
                .startDate(startDate)
                .endDate(endDate)
                .viewCount(0)
                .peopleNumber(peopleNumber)
                .build();
    }

    public String routeToString() {

        StringBuilder sb = new StringBuilder();
        for (NewFeedRequest feed : feeds) {
            sb
                    .append(feed.getNthDay()).append("|")
                    .append(feed.getSequence()).append("|")
                    .append(feed.getLatitude()).append("|")
                    .append(feed.getLongitude()).append("|")
                    .append(feed.getAddress()).append("|")
                    .append(feed.getLocation()).append("|");
        }
        return sb.toString();
    }
}
