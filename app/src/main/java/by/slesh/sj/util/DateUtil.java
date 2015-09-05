package by.slesh.sj.util;

import java.util.Date;

/**
 * Created by slesh on 05.09.2015.
 */
public final class DateUtil {
    public static final Integer getUnixTime(){
        return (int)(new Date().getTime() / 1000L);
    }
}
