package ssafy.narou.pjt.global.auth.oauthAuth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.OAuthLoginUser;
import ssafy.narou.pjt.global.auth.oauthAuth.entity.ProviderType;
import ssafy.narou.pjt.global.auth.oauthAuth.repository.OAuth2AuthorizedMemberRepository;
import ssafy.narou.pjt.global.error.InvalidAuthenticationException;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.repository.MemberRepository;

import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@Service
public class NarouOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired private OAuth2AuthorizedMemberRepository oAuth2AuthorizedMemberRepository;
    @Autowired private MemberRepository memberRepository;
    private final OAuth2AuthorizedClientConverter oAuth2AuthorizedClientConverter;

    public NarouOAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oAuth2AuthorizedClientConverter = new OAuth2AuthorizedClientConverter(clientRegistrationRepository);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalEmail) {
        Assert.hasText(clientRegistrationId, "principalEmail cannot be empty");
        Assert.hasText(principalEmail, "principalEmail cannot be empty");

        ClientRegistration registration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration == null){
            log.info("clientRegistration is null  ID : {}", clientRegistrationId);
            return null;
        }

        Optional<OAuthLoginUser> oAuthLoginUser = oAuth2AuthorizedMemberRepository.findByEmail(principalEmail);
        if (oAuthLoginUser.isEmpty()){
            log.info("can't find oauthLoginUser Email : {}", principalEmail);
            return null;
        }
        OAuth2AuthorizedClient authorizedClient =
                oAuth2AuthorizedClientConverter.convertUserToClient(oAuthLoginUser.get(), principalEmail, clientRegistrationId);

        return (T) authorizedClient;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");

        boolean existsAuthorizedClient =
                null != this.loadAuthorizedClient(authorizedClient.getClientRegistration().getRegistrationId()
                        , principal.getName());

//        if (existsAuthorizedClient){
//            updateAuthorizedClient(authorizedClient, principal);
//        }else {
//            insertAuthorizedClient(authorizedClient, principal);
//        }
    }

    private void updateAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal){
        Optional<Member> member = memberRepository.findMemberByEmail(principal.getName());

        if (member.isPresent()){
            OAuthLoginUser oAuthLoginUser = oAuth2AuthorizedClientConverter.convertClientToUser(authorizedClient, member.get().getUserId());
            oAuth2AuthorizedMemberRepository.save(oAuthLoginUser);
        }else {
            throw new InvalidAuthenticationException("회원 정보 저장 오류");
        }
    }

    private void insertAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal){
        Optional<Member> member = memberRepository.findMemberByEmail(principal.getName());

        if (member.isPresent()){
            OAuthLoginUser oAuthLoginUser = oAuth2AuthorizedClientConverter.convertClientToUser(authorizedClient, member.get().getUserId());
            oAuth2AuthorizedMemberRepository.save(oAuthLoginUser);
        }else {
            throw new InvalidAuthenticationException("회원 정보 저장 오류");
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalEmail) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalEmail, "principalEmail cannot be empty");
        oAuth2AuthorizedMemberRepository.removeOAuth2Member(principalEmail);
    }

    @RequiredArgsConstructor
    public static class OAuth2AuthorizedClientConverter {

        private final ClientRegistrationRepository clientRegistrationRepository;

        public OAuthLoginUser convertClientToUser(OAuth2AuthorizedClient authorizedClient, Long userId){
            String providerName = authorizedClient.getClientRegistration().getClientName();

            return OAuthLoginUser.builder()
                    .socialLoginId(userId)
                    .accessToken(authorizedClient.getAccessToken().getTokenValue())
                    .provider(ProviderType.valueOf(providerName.toUpperCase()))
                    .build();
        }

        public OAuth2AuthorizedClient convertUserToClient(OAuthLoginUser oauthLoginUser, String principalEmail, String clientRegistrationId){
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    oauthLoginUser.getAccessToken(),
                    oauthLoginUser.getTokenUpdateTime().atZone(ZoneId.systemDefault()).toInstant(),
                    null);    // 조심!!!!!!!!

            return new OAuth2AuthorizedClient(clientRegistration, principalEmail, accessToken);
        }
    }
}