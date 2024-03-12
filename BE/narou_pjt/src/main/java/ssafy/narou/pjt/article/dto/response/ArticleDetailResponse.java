package ssafy.narou.pjt.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ArticleDetailResponse {

    private Long articleId;
    private SimpleUserProfile member;
    private String title;
    private List<HashTagResponse> hashtags;
    private Integer likeCount;
    private List<FeedResponse> feeds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private Long dateDiff;
    private Integer viewCount;
    private Integer peopleNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    @Setter
    private Boolean scraped;
    @Setter
    private Boolean liked;

    public static ArticleDetailResponse of(Article article, List<FeedResponse> feeds, List<HashTagResponse> hashtags){
        Member member = article.getMember();
        SimpleUserProfile profile = SimpleUserProfile.builder()
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();

        return ArticleDetailResponse.builder()
                .articleId(article.getId())
                .member(profile)
                .title(article.getTitle())
                .hashtags(hashtags)
                .likeCount(article.getLikeCount())
                .feeds(feeds)
                .startDate(article.getStartDate())
                .endDate(article.getEndDate())
                .dateDiff(getDateDiff(article.getStartDate(),article.getEndDate()))
                .viewCount(article.getViewCount())
                .peopleNumber(article.getPeopleNumber())
                .createdTime(article.getCreatedTime())
                .build();
    }

    private static Long getDateDiff(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1L;
    }

}
