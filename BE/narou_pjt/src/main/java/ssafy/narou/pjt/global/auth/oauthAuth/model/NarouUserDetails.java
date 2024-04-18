package ssafy.narou.pjt.global.auth.oauthAuth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ssafy.narou.pjt.global.auth.passwordAuth.entity.PasswordLoginUser;
import ssafy.narou.pjt.member.entity.Member;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/** Authentication 상속 받는 객체
 *  OAuth2User와 PasswordUser를 SecurityContext에 저장하기 위한 용도로
 *  attributes에 유저 상세 정보가 들어있음(nickname, profileImage)
 */
@NoArgsConstructor
public class NarouUserDetails implements UserDetails, OAuth2User {

    @Getter
    private Long userId;
    private String name;
    private String nickname;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override  // oauth2user
    public String getName() {
        return name;
    }

    @Override  // userdetails, oauth2user
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override  // userdetails, oauth2user
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override  // userdetails
    public String getPassword() {
        return password;
    }

    @Override  // userdetails
    public String getUsername() {
        return nickname;
    }

    @Override  // userdetails
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override  // userdetails
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override  // userdetails
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override  // userdetails
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public NarouUserDetails(Long userId, String name, String nickname, String password, Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static NarouUserDetails ofOAuthUser(OAuth2UserInfo oAuth2UserInfo, Member member){
        return NarouUserDetails.builder()
                .userId(member.getUserId())
                .name(oAuth2UserInfo.getEmail())
                .nickname(oAuth2UserInfo.getEmail())
                .attributes(oAuth2UserInfo.getAttributes())
                .authorities(oAuth2UserInfo.getAuthorities())
                .build();
    }

    public static NarouUserDetails ofPasswordUser(PasswordLoginUser passwordLoginUser){
        return NarouUserDetails.builder()
                .userId(passwordLoginUser.getPasswordLoginUser().getUserId())
                .name(passwordLoginUser.getPasswordLoginUser().getEmail())
                .nickname(passwordLoginUser.getPasswordLoginUser().getEmail())
                .password(passwordLoginUser.getPassword())
                .build();
    }

    // userId와 authorities 동일하면 equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NarouUserDetails that = (NarouUserDetails) o;
        if (this.authorities == null || that.authorities == null) return false;
        return Objects.equals(this.userId, that.userId)
                && this.authorities.size() == that.authorities.size()
                && this.authorities.containsAll(that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, authorities);
    }
}
