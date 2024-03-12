package ssafy.narou.pjt.global.auth.oauthAuth.repository;

import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.OAuthLoginUser;

import java.util.Optional;

public interface OAuth2AuthorizedMemberRepositoryCustom {

    Optional<OAuthLoginUser> findByEmail(String email);
    void updateOAuth2Member(OAuthLoginUser oAuthLoginUser, String principalEmail);
    @Transactional
    void insertOAuth2Member(OAuthLoginUser oAuthLoginUser, String principalEmail);
    void removeOAuth2Member(String principalEmail);
}
