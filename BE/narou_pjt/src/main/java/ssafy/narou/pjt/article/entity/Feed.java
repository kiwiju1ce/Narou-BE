package ssafy.narou.pjt.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "feed")
public class Feed{

    @Id //피드 아이디
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;
    
    //피드를 포함하는 게시글의 아이디
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
    //일차
    @Column(name = "date")
    private Integer nthDay;
    //일차별 순서
    @Column(name = "sequence")
    private Integer sequence;
    //input 내용
    private String content;
    //위도
    private Double latitude;
    //경도
    private Double longitude;
    //시구(서울시 강남구)
    private String address;
    //장소명
    private String location;
    //파일 경로
    @Column(name = "file_path")
    private String filePath;
    //파일 타입
    @Column(name = "file_type")
    private String fileType;

    @Builder
    public Feed(Article article, Integer nthDay, Integer sequence, String content, Double latitude, Double longitude,
                String address, String location, String filePath, String fileType) {
        this.article = article;
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

}
