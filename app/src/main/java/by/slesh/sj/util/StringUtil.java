package by.slesh.sj.util;

/**
 * Created by slesh on 06.09.2015.
 */
public final class StringUtil {
    public static final boolean isBlank(String s) {
        int strLen;
        if (s == null || (strLen = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(s.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
