package ssafy.narou.pjt.global.utils;

import java.time.LocalDateTime;

public class TimeUtils {

    public static boolean checkExpiration(LocalDateTime time, Long expiration) {
        return LocalDateTime.now().isAfter(time.plusSeconds(expiration));
    }
}
