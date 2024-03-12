package ssafy.narou.pjt.global.auth.passwordAuth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPasswordLoginUser is a Querydsl query type for PasswordLoginUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPasswordLoginUser extends EntityPathBase<PasswordLoginUser> {

    private static final long serialVersionUID = 846127015L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPasswordLoginUser passwordLoginUser1 = new QPasswordLoginUser("passwordLoginUser1");

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final NumberPath<Long> passwordLoginId = createNumber("passwordLoginId", Long.class);

    public final ssafy.narou.pjt.member.entity.QMember passwordLoginUser;

    public QPasswordLoginUser(String variable) {
        this(PasswordLoginUser.class, forVariable(variable), INITS);
    }

    public QPasswordLoginUser(Path<? extends PasswordLoginUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPasswordLoginUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPasswordLoginUser(PathMetadata metadata, PathInits inits) {
        this(PasswordLoginUser.class, metadata, inits);
    }

    public QPasswordLoginUser(Class<? extends PasswordLoginUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.passwordLoginUser = inits.isInitialized("passwordLoginUser") ? new ssafy.narou.pjt.member.entity.QMember(forProperty("passwordLoginUser")) : null;
    }

}

