package ssafy.narou.pjt.global.auth.oauthAuth.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.member.entity.LoginType;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * username : 사용자 email
 * password :
 */
@Getter
public class PasswordUserInfo extends UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final String password;

    public PasswordUserInfo(Set<GrantedAuthority> authorities, String username, String password) {
        super(authorities);
        this.username = username;
        this.password = password;
    }

    @Builder
    public PasswordUserInfo(String username, String password) {
        super(Collections.unmodifiableSet(new LinkedHashSet<>(LoginType.PASSWORD_USER.getLoginAuthority())));
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }}
