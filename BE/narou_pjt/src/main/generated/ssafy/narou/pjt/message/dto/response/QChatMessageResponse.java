package ssafy.narou.pjt.message.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ssafy.narou.pjt.message.dto.response.QChatMessageResponse is a Querydsl Projection type for ChatMessageResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QChatMessageResponse extends ConstructorExpression<ChatMessageResponse> {

    private static final long serialVersionUID = 1268182960L;

    public QChatMessageResponse(com.querydsl.core.types.Expression<? extends ssafy.narou.pjt.member.dto.response.SimpleUserProfile> profile, com.querydsl.core.types.Expression<String> chatRoomName, com.querydsl.core.types.Expression<Long> messageId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> latest_time) {
        super(ChatMessageResponse.class, new Class<?>[]{ssafy.narou.pjt.member.dto.response.SimpleUserProfile.class, String.class, long.class, String.class, java.time.LocalDateTime.class}, profile, chatRoomName, messageId, content, latest_time);
    }

}

