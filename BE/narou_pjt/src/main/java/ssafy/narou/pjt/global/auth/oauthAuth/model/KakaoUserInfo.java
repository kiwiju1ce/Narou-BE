package ssafy.narou.pjt.global.auth.oauthAuth.model;

import org.springframework.security.core.GrantedAuthority;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;

import java.util.Map;
import java.util.Set;

public class KakaoUserInfo extends OAuth2UserInfo{

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    public String getName(){
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return properties != null ?
                (String) properties.get("nickname") : null;
    }

    @Override
    public String getNickname() {
        return getName();
    }

    public String getEmail(){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return kakaoAccount != null ?
                (String) kakaoAccount.get("email") : null;
    }

    public String getProfileImage(){
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return properties != null ?
                (String) properties.get("profile_image") : null;
    }

    @Override
    public String getProviderType() {
        return ProviderType.KAKAO.name();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(){
        return super.getAuthorities();
    }
}
