package ssafy.narou.pjt.global.auth.oauthAuth.model;

import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;

import java.util.Map;
import java.util.Set;

public class GoogleUserInfo extends OAuth2UserInfo{

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getProviderType() {
        return ProviderType.GOOGLE.name();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(){
        return super.getAuthorities();
    }
}
