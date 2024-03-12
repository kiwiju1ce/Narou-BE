package ssafy.narou.pjt.global.auth.oauthAuth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ssafy.narou.pjt.member.entity.LoginType;

import java.util.*;

@Getter
@ToString
public abstract class OAuth2UserInfo extends UserDetails implements OAuth2User {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        super(Collections.unmodifiableSet(new LinkedHashSet<>(LoginType.OAUTH2_USER.getLoginAuthority())));
        this.attributes = attributes;
    }

    protected OAuth2UserInfo(Map<String, Object> attributes, Set<GrantedAuthority> authorities){
        super(authorities);
        this.attributes = attributes;
    }

    public abstract String getName();
    public abstract String getNickname();
    public abstract String getEmail();
    public abstract String getProfileImage();
    public abstract String getProviderType();

}
