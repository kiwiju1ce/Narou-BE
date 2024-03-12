package ssafy.narou.pjt.member.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ssafy.narou.pjt.member.dto.response.QSimpleUserProfile is a Querydsl Projection type for SimpleUserProfile
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSimpleUserProfile extends ConstructorExpression<SimpleUserProfile> {

    private static final long serialVersionUID = 873211147L;

    public QSimpleUserProfile(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> profileImage, com.querydsl.core.types.Expression<String> introduction) {
        super(SimpleUserProfile.class, new Class<?>[]{long.class, String.class, String.class, String.class}, userId, nickname, profileImage, introduction);
    }

    public QSimpleUserProfile(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> profileImage, com.querydsl.core.types.Expression<String> introduction, com.querydsl.core.types.Expression<Integer> followers, com.querydsl.core.types.Expression<Integer> followings) {
        super(SimpleUserProfile.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, int.class}, userId, nickname, profileImage, introduction, followers, followings);
    }

}

