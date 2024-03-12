package ssafy.narou.pjt.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.BaseEntity;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted")
    private Integer deleted;

    @Builder
    public Comment(Article article, Member member, String content, Integer deleted) {
        this.article = article;
        this.member = member;
        this.content = content;
        this.deleted = deleted;
    }

    public void update(String content){
        this.content=content;
    }

    public void delete(){
        this.deleted=1;
    }
}
