package ssafy.narou.pjt.global.auth.passwordAuth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.narou.pjt.global.auth.passwordAuth.entity.PasswordLoginUser;

import java.util.Optional;

public interface PasswordMemberRepositoryCustom {

    @Query("SELECT p FROM PasswordLoginUser p JOIN FETCH p.passwordLoginUser m WHERE m.email = :email")
    Optional<PasswordLoginUser> findByEmail(@Param("email") String email);
}
