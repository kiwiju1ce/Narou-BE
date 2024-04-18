package ssafy.narou.pjt.global.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationUtilsTest {

    @Test
    public void getAuthoritiesTest() {
        String string = "authority1,authority2,authority3";
        Set<GrantedAuthority> authorities = AuthenticationUtils.getAuthorities(string);

        assertThat(authorities.size()).isEqualTo(3);
        assertThat(authorities).contains(new SimpleGrantedAuthority("authority1"));
        assertThat(authorities).contains(new SimpleGrantedAuthority("authority2"));
        assertThat(authorities).contains(new SimpleGrantedAuthority("authority3"));
    }
}