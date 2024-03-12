package ssafy.narou.pjt.global.auth.passwordAuth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.auth.passwordAuth.entity.PasswordLoginUser;
import ssafy.narou.pjt.global.auth.passwordAuth.repository.PasswordMemberRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NarouPasswordAuthorizedUserService implements UserDetailsService {

    private final PasswordMemberRepository passwordMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Assert.hasText(email, "email cannot be empty");
        Optional<PasswordLoginUser> member = passwordMemberRepository.findByEmail(email);

        return member
                .map(NarouUserDetails::ofPasswordUser)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 회원입니다."));
    }
}
