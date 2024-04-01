package ssafy.narou.pjt.global.auth.jwtAuth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credential;

    public JwtAuthenticationToken(Object principal, Object credential) {
        super(null);
        this.principal = principal;
        this.credential = credential;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credential = credential;
        setAuthenticated(true);
    }

    public static JwtAuthenticationToken unauthenticated(Object principal, Object credential) {
        return new JwtAuthenticationToken(principal, credential);
    }

    public static JwtAuthenticationToken authenticated(Object principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(principal, credential, authorities);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credential;
    }

}
