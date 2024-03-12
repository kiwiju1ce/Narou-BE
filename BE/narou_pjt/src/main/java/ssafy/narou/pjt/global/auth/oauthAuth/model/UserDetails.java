package ssafy.narou.pjt.global.auth.oauthAuth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * 유저 정보를 나타내는 클래스
 * 소셜 로그인 유저는 OAuth2UserInfo, 일반 로그인 유저는 PasswordUserInfo로 상속 받음
 * authorities 필드로 로그인 방식을 구분하고 그에 따라 구체 클래스를 지정하면 됨.
 */
@Getter
@AllArgsConstructor
public abstract class UserDetails {
    public Set<GrantedAuthority> authorities;
}
