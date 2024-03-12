package ssafy.narou.pjt.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum LoginType {
    OAUTH2_USER(Collections.singleton(new SimpleGrantedAuthority("OAUTH2_USER"))),
    PASSWORD_USER(Collections.singleton(new SimpleGrantedAuthority("PASSWORD_USER")));

    public final Set<GrantedAuthority> loginAuthority;
}
