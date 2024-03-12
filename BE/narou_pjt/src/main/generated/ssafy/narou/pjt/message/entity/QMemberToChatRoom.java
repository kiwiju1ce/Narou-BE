package ssafy.narou.pjt.message.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberToChatRoom is a Querydsl query type for MemberToChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberToChatRoom extends EntityPathBase<MemberToChatRoom> {

    private static final long serialVersionUID = -270863659L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberToChatRoom memberToChatRoom = new QMemberToChatRoom("memberToChatRoom");

    public final ssafy.narou.pjt.QBaseEntity _super = new ssafy.narou.pjt.QBaseEntity(this);

    public final QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final ssafy.narou.pjt.member.entity.QMember member;

    public final NumberPath<Long> memberRoomId = createNumber("memberRoomId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QMemberToChatRoom(String variable) {
        this(MemberToChatRoom.class, forVariable(variable), INITS);
    }

    public QMemberToChatRoom(Path<? extends MemberToChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberToChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberToChatRoom(PathMetadata metadata, PathInits inits) {
        this(MemberToChatRoom.class, metadata, inits);
    }

    public QMemberToChatRoom(Class<? extends MemberToChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new ssafy.narou.pjt.member.entity.QMember(forProperty("member")) : null;
    }

}

