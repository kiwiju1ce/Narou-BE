package ssafy.narou.pjt.global.auth.oauthAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.OAuthLoginUser;

import java.util.Optional;

public interface OAuth2AuthorizedMemberRepository extends JpaRepository<OAuthLoginUser, Long>, OAuth2AuthorizedMemberRepositoryCustom {
}
