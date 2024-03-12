package ssafy.narou.pjt.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.narou.pjt.member.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    void updateRefreshToken(Long userId, String refreshToken);
}
