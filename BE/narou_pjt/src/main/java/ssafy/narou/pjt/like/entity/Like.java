package ssafy.narou.pjt.like.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.BaseEntity;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "likes")
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Builder
    public Like(Member member, Article article) {
        this.member = member;
        this.article = article;
    }
}
