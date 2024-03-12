package ssafy.narou.pjt.global.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.error.InvalidAuthenticationException;

import java.util.Optional;

public class AuthenticationUtils {

    private static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 로그인 유저와 비로그인 유저 구분하여 반환
     * @return
     */
    public static Optional<NarouUserDetails> getAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof NarouUserDetails) {
            return Optional.of((NarouUserDetails) principal);
        } else if (principal.equals(ANONYMOUS_USER)){
            return Optional.empty();
        }else {
            throw new InvalidAuthenticationException("잘못된 권한 정보입니다.");
        }
    }
}
