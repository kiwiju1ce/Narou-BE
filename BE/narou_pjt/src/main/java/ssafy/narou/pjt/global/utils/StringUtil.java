package ssafy.narou.pjt.global.utils;

public abstract class StringUtil {

    public static String safeString(Object string) {
        return string == null ? "" : (String) string;
    }
}
