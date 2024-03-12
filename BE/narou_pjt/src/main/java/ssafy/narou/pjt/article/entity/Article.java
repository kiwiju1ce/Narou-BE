package ssafy.narou.pjt.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ssafy.narou.pjt.BaseEntity;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.scrap.entity.Scrap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "article")
@ToString(exclude = {"scraps"})
public class Article extends BaseEntity {

    @Id //게시글 아이디
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    //게시글 작성자 member
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    //제목
    @Column(name = "title", nullable = false)
    private String title;

    //해시태그
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "article_to_tag")
    private List<ArticleToTag> articleToTags;

    //좋아요 수
    @ColumnDefault("0")
    @Column(name = "likes", nullable = false)
    private Integer likeCount;

    //게시글에 해당하는 피드 리스트
    @Column(name = "article_feeds")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
//    @OrderBy("nthDay ASC") // order는 어떻게 처리해야할까..?
    private List<Feed> feeds = new LinkedList<>();

    //썸네일 주소
    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    //text로 뽑아낼 route
    @Column(name = "route")
    private String route;

    //시작일자
    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    //종료일자
    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    //조회수
    @ColumnDefault("0")
    @Column(name = "view_cnt",nullable = false)
    private Integer viewCount;

    //스크랩
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    Set<Scrap> scraps = new HashSet<>();

    // 사람 수
    @ColumnDefault("1")
    @Column(name = "peopleNumber", nullable = false)
    private Integer peopleNumber;


    @Builder
    public Article(Long id,Member member, String title, Integer likeCount, List<Feed> feeds, String thumbnailPath, String route, LocalDate startDate, LocalDate endDate, Integer viewCount, Integer peopleNumber) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.likeCount = likeCount;
        this.feeds = feeds;
        this.thumbnailPath = thumbnailPath;
        this.route = route;
        this.startDate = startDate;
        this.endDate = endDate;
        this.viewCount = viewCount;
        this.peopleNumber = peopleNumber;
    }
}




