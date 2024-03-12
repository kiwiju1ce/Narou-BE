package ssafy.narou.pjt.article.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 195763418L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final ssafy.narou.pjt.QBaseEntity _super = new ssafy.narou.pjt.QBaseEntity(this);

    public final ListPath<ArticleToTag, QArticleToTag> articleToTags = this.<ArticleToTag, QArticleToTag>createList("articleToTags", ArticleToTag.class, QArticleToTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final ListPath<Feed, QFeed> feeds = this.<Feed, QFeed>createList("feeds", Feed.class, QFeed.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ssafy.narou.pjt.member.entity.QMember member;

    public final NumberPath<Integer> peopleNumber = createNumber("peopleNumber", Integer.class);

    public final StringPath route = createString("route");

    public final SetPath<ssafy.narou.pjt.scrap.entity.Scrap, ssafy.narou.pjt.scrap.entity.QScrap> scraps = this.<ssafy.narou.pjt.scrap.entity.Scrap, ssafy.narou.pjt.scrap.entity.QScrap>createSet("scraps", ssafy.narou.pjt.scrap.entity.Scrap.class, ssafy.narou.pjt.scrap.entity.QScrap.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath thumbnailPath = createString("thumbnailPath");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new ssafy.narou.pjt.member.entity.QMember(forProperty("member")) : null;
    }

}

