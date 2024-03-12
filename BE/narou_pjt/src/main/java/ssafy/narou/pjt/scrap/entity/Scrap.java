package ssafy.narou.pjt.scrap.entity;

import jakarta.persistence.*;
import lombok.*;
import ssafy.narou.pjt.BaseEntity;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.member.entity.Member;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "scrap")
public class Scrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Scrap(Member member, Article article) {
        this.member = member;
        this.article = article;
    }
}