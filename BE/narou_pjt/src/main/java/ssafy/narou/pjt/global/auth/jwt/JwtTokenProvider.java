package ssafy.narou.pjt.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;
import ssafy.narou.pjt.global.properties.TokenProperties;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.entity.RefreshToken;
import ssafy.narou.pjt.member.repository.MemberRepository;
import ssafy.narou.pjt.member.repository.RefreshTokenRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

//    private static final String AUTHORITIES_KEY = "Authority";
    private static final String ACCESS_TOKEN_NAME = "access-token";
    private static final String REFRESH_TOKEN_NAME = "refresh-token";

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    private SecretKey key;

    public JwtTokenProvider(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(NarouUserDetails userDetails, long accessTokenExpiration) {
        return new JwtToken(userDetails, key, accessTokenExpiration).getToken();
    }

    public Authentication getAuthentication(JwtToken token) {

        Claims claims = token.getTokenClaims();
//
//        Collection<GrantedAuthority> authorities =
//                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toSet());

        log.debug("claims subject := {}", claims.getSubject());
        NarouUserDetails principal = NarouUserDetails.builder()
//                .authorities(authorities)
                .userId(((Integer) claims.get("id")).longValue())
                .name(claims.getSubject())
                .build();

        return new UsernamePasswordAuthenticationToken(principal, null, null);
    }

    public JwtToken convertJwtToken(String tokenValue) {
        return new JwtToken(tokenValue, key);
    }

    public String[] processJwtTokenLoad(HttpServletResponse response, NarouUserDetails userDetails){

        String accessToken = createToken(userDetails, tokenProperties.getAccessTokenExpiration());
        String refreshToken = createToken(userDetails, tokenProperties.getRefreshTokenExpiration());

        Optional<Member> member = memberRepository.findById(userDetails.getUserId());
//        refreshTokenRepository.save(
//                RefreshToken.of(member.get(), refreshToken)
//        );
        return new String[] {accessToken, refreshToken};
        // 헤더에 jwt 정보 넣기
//        JwtUtils.tokenLoad(response, JwtTokenProvider.ACCESS_TOKEN_NAME, accessToken);
//        JwtUtils.tokenLoad(response, JwtTokenProvider.REFRESH_TOKEN_NAME, refreshToken);
    }
}
