package ssafy.narou.pjt.global.auth.passwordAuth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.global.auth.oauthAuth.model.PasswordUserInfo;
import ssafy.narou.pjt.global.auth.passwordAuth.entity.PasswordLoginUser;

import java.util.Map;
import java.util.Optional;

public interface PasswordMemberRepository extends JpaRepository<PasswordLoginUser, Integer>, PasswordMemberRepositoryCustom{
}
