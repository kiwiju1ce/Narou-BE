package ssafy.narou.pjt.global.auth.oauthAuth.model;

import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;

import java.util.Map;
import java.util.Set;

public class NaverUserInfo extends OAuth2UserInfo{

    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    public String getName(){
        return getNickname();
    }

    @Override
    public String getNickname() {
        Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("nickname");

    }

    public String getEmail(){
        Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("email");
    }

    public String getProfileImage(){
        Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("profile_image");
    }

    @Override
    public String getProviderType() {
        return ProviderType.NAVER.name();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(){
        return super.getAuthorities();
    }
}
