package ssafy.narou.pjt.global.auth.oauthAuth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOAuthLoginUser is a Querydsl query type for OAuthLoginUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOAuthLoginUser extends EntityPathBase<OAuthLoginUser> {

    private static final long serialVersionUID = 375319559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOAuthLoginUser oAuthLoginUser1 = new QOAuthLoginUser("oAuthLoginUser1");

    public final StringPath accessToken = createString("accessToken");

    public final ssafy.narou.pjt.member.entity.QMember oAuthLoginUser;

    public final EnumPath<ProviderType> provider = createEnum("provider", ProviderType.class);

    public final NumberPath<Long> socialLoginId = createNumber("socialLoginId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> tokenUpdateTime = createDateTime("tokenUpdateTime", java.time.LocalDateTime.class);

    public QOAuthLoginUser(String variable) {
        this(OAuthLoginUser.class, forVariable(variable), INITS);
    }

    public QOAuthLoginUser(Path<? extends OAuthLoginUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOAuthLoginUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOAuthLoginUser(PathMetadata metadata, PathInits inits) {
        this(OAuthLoginUser.class, metadata, inits);
    }

    public QOAuthLoginUser(Class<? extends OAuthLoginUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oAuthLoginUser = inits.isInitialized("oAuthLoginUser") ? new ssafy.narou.pjt.member.entity.QMember(forProperty("oAuthLoginUser")) : null;
    }

}

