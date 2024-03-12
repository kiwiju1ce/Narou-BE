package ssafy.narou.pjt.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleToTag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articleToTag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private HashTag hashtag;

    @Builder
    public ArticleToTag(Article article, HashTag hashtag) {
        this.article = article;
        this.hashtag = hashtag;
    }
}
