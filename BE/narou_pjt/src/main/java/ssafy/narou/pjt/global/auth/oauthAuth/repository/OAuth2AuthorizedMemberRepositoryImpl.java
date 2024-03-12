package ssafy.narou.pjt.global.auth.oauthAuth.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.OAuthLoginUser;

import java.time.LocalDateTime;
import java.util.Optional;

import static ssafy.narou.pjt.member.entity.QMember.member;
import static ssafy.narou.pjt.global.auth.oauthAuth.entity.QOAuthLoginUser.oAuthLoginUser1;

@Repository
@RequiredArgsConstructor
public class OAuth2AuthorizedMemberRepositoryImpl implements OAuth2AuthorizedMemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<OAuthLoginUser> findByEmail(String email) {
        return Optional.ofNullable(
                queryFactory.selectFrom(oAuthLoginUser1)
                        .leftJoin(member)
                        .on(oAuthLoginUser1.socialLoginId.eq(member.userId))
                        .where(member.email.eq(email))
                        .fetchFirst()
        );
    }

    @Override
    public void updateOAuth2Member(OAuthLoginUser authLoginUser, String email) {
        queryFactory.update(oAuthLoginUser1)
                .set(oAuthLoginUser1.accessToken, authLoginUser.getAccessToken())
                .set(oAuthLoginUser1.provider, authLoginUser.getProvider())
                .set(oAuthLoginUser1.tokenUpdateTime, LocalDateTime.now())
                .where(oAuthLoginUser1.socialLoginId.eq(
                                JPAExpressions
                                    .select(member.userId)
                                    .from(member)
                                    .where(member.email
                                            .eq(email)
                                    )
                        )
                ).execute();
    }

    @Override
    public void insertOAuth2Member(OAuthLoginUser oAuthLoginUser, String email) {

    }

    @Override
    public void removeOAuth2Member(String principalEmail) {
        queryFactory.delete(oAuthLoginUser1)
                .where(oAuthLoginUser1.socialLoginId.eq(
                        JPAExpressions
                            .select(member.userId)
                            .from(member)
                            .where(member.email
                                    .eq(principalEmail)
                            )
                        )
                ).execute();
    }
}
