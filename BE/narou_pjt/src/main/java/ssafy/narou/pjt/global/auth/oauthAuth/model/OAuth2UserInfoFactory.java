package ssafy.narou.pjt.global.auth.oauthAuth.model;

import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;

import java.util.Map;

public abstract class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleUserInfo(attributes);
            case KAKAO: return new KakaoUserInfo(attributes);
            case NAVER: return new NaverUserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid ProviderType");
        }
    }
}
