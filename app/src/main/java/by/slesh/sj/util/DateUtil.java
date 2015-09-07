package by.slesh.sj.util;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by slesh on 05.09.2015.
 */
public final class DateUtil {
    public static final Integer getUnixTime() {
        return (int) (new Date().getTime() / 1000L);
    }

    public static final String getDate(Long millisecond) {
        return DateFormat.format("MM-dd-yyyy hh:mm", new Date(millisecond)).toString();
    }

    public static final String getDate(String format) {
        return DateFormat.format(format, new Date(new Date().getTime())).toString();
    }
}
