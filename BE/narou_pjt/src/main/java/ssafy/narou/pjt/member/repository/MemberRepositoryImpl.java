package ssafy.narou.pjt.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.member.dto.response.QSimpleUserProfile;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;

import java.util.Optional;

import static ssafy.narou.pjt.member.entity.QFollow.follow;
import static ssafy.narou.pjt.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

//    @Override
//    public Optional<Member> findByEmail(String email) {
//        QMember emailMember = new QMember("emailMember");
//
//        return Optional.ofNullable(queryFactory.selectFrom(emailMember)
//                .where(emailMember.email.eq(email))
//                .fetchFirst()
//        );
//    }

//    @Override
//    public Optional<Member> findByNickname(String nickname) {
//        QMember nickMember = new QMember("nickMember");
//
//        return Optional.ofNullable(queryFactory.selectFrom(nickMember)
//                .where(nickMember.nickname.eq(nickname))
//                .fetchFirst()
//        );
//    }

    @Override
    public Optional<SimpleUserProfile> findProfileById(Long userId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SimpleUserProfile.class,
                        member.userId, member.nickname, member.profileImage,
                        member.introduction, member.followers, member.followings)
                )
                .from(member)
                .where(member.userId.eq(userId))
                .fetchFirst()
        );
    }

    @Override
    public Optional<SimpleUserProfile> findProfileByIdLoginUser(Long searchedUserId, Long userId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SimpleUserProfile.class,
                        member.userId, member.nickname, member.profileImage, member.introduction)
                )
                .from(member)
                .where(member.userId.eq(userId))
                .fetchFirst()
        );
    }

    @Override
    public Optional<SimpleUserProfile> findProfileByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SimpleUserProfile.class,
                        member.userId, member.nickname, member.profileImage, member.introduction)
                )
                .from(member)
                .where(member.email.eq(email))
                .fetchFirst()
        );
    }

    @Override
    public void updateUserProfile(Member profile) {
        queryFactory.update(member)
                .set(member.nickname, profile.getNickname())
                .set(member.profileImage, profile.getProfileImage())
                .set(member.introduction, profile.getIntroduction())
                .where(member.userId.eq(profile.getUserId()))
                .execute();

    }

    @Override
    public void updateRefreshToken(String name, String refreshToken) {

    }
}
