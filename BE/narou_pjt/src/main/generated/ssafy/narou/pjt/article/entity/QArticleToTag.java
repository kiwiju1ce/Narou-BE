package ssafy.narou.pjt.article.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleToTag is a Querydsl query type for ArticleToTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleToTag extends EntityPathBase<ArticleToTag> {

    private static final long serialVersionUID = -944091547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleToTag articleToTag = new QArticleToTag("articleToTag");

    public final QArticle article;

    public final QHashTag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QArticleToTag(String variable) {
        this(ArticleToTag.class, forVariable(variable), INITS);
    }

    public QArticleToTag(Path<? extends ArticleToTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleToTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleToTag(PathMetadata metadata, PathInits inits) {
        this(ArticleToTag.class, metadata, inits);
    }

    public QArticleToTag(Class<? extends ArticleToTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
        this.hashtag = inits.isInitialized("hashtag") ? new QHashTag(forProperty("hashtag")) : null;
    }

}

