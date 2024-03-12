package ssafy.narou.pjt.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFollowId is a Querydsl query type for FollowId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFollowId extends BeanPath<FollowId> {

    private static final long serialVersionUID = 675580474L;

    public static final QFollowId followId = new QFollowId("followId");

    public final NumberPath<Long> followedId = createNumber("followedId", Long.class);

    public final NumberPath<Long> followingId = createNumber("followingId", Long.class);

    public QFollowId(String variable) {
        super(FollowId.class, forVariable(variable));
    }

    public QFollowId(Path<? extends FollowId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFollowId(PathMetadata metadata) {
        super(FollowId.class, metadata);
    }

}

