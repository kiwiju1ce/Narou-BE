package ssafy.narou.pjt.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class TimeUtils {

    public static boolean checkExpiration(LocalDateTime time, Long expiration) {
        return LocalDateTime.now().isAfter(time.plusSeconds(expiration));
    }

    public static LocalDateTime convertLDT(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
