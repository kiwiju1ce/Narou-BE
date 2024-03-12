package ssafy.narou.pjt.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.member.dto.response.SimpleUserProfile;
import ssafy.narou.pjt.member.entity.Follow;
import ssafy.narou.pjt.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ssafy.narou.pjt.member.entity.QFollow.follow;
import static ssafy.narou.pjt.member.entity.QMember.member;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FollowInfoRepositoryImpl implements FollowInfoRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Follow> loadFollow(Long followedId, Long followingId) {
        return Optional.ofNullable(queryFactory
                        .selectFrom(follow)
                        .where(follow.followId.followedId.eq(followedId),
                                (follow.followId.followingId.eq(followingId))
                        )
                .fetchFirst()
        );
    }

    @Override
    public List<SimpleUserProfile> findFollowerById(Long userId) {
        return queryFactory.select(Projections.constructor
                        (SimpleUserProfile.class, member.userId, member.nickname, member.profileImage, member.introduction))
                .from(follow)
                .leftJoin(follow.followingMember, member)
                .where(follow.followedMember.userId.eq(userId), follow.activated.eq(true))
                .fetch();
    }

    @Override
    public List<SimpleUserProfile> findFollowById(Long userId) {
        return queryFactory.select(Projections.constructor
                        (SimpleUserProfile.class, member.userId, member.nickname, member.profileImage, member.introduction))
                .from(follow)
                .leftJoin(follow.followedMember, member)
                .where(follow.followingMember.userId.eq(userId), follow.activated.eq(true))
                .fetch();
    }

//    @Override
//    public void createFollow(Member followedId, Member followingId) {
//        queryFactory.insert(follow)
//                .set(follow.followedMember, followedId)
//                .set(follow.followingMember, followingId)
//                .set(follow.activated, true)
//                .set(follow.createdTime, LocalDateTime.now())
//                .execute();
////        );
//    }

    // 나중에 고치자..
    @Override
    public void createFollow(Long followedId, Long followingId, boolean isActivated){
        Follow created = Follow.builder()
                .followedMember(entityManager.getReference(Member.class, followedId))
                .followingMember(entityManager.getReference(Member.class, followingId))
                .activated(isActivated)
                .createdTime(LocalDateTime.now())
                .build();

        entityManager.persist(created);
    }

    @Override
    public void updateFollow(Long followedId, Long followingId, boolean status) {
//        queryFactory.update(follow)
//                .set(follow.activated, status)
//                .set(follow.createdTime, LocalDateTime.now())
//                .where(follow.followId.followingId.eq(followingId),
//                        follow.followId.followedId.eq(followedId)
//                )
//                .execute();

        queryFactory.update(follow)
                .set(follow.activated, status)
                .set(follow.createdTime, LocalDateTime.now())
                .where(follow.followedMember.userId.eq(followedId)
                        .and(follow.followingMember.userId.eq(followingId)))
                .execute();
    }

}
