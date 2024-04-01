package ssafy.narou.pjt.global.auth.oauthAuth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.auth.oauthAuth.model.OAuth2UserInfo;
import ssafy.narou.pjt.global.auth.oauthAuth.model.OAuth2UserInfoFactory;
import ssafy.narou.pjt.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NarouOAuth2UserInfoService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        // 권한 정보 업데이트할 것.
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        Member member = memberRepository.findMemberByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> memberRepository.save(Member.createOAuth2User(oAuth2UserInfo)));


        return NarouUserDetails.ofOAuthUser(oAuth2UserInfo, member);
    }
}
