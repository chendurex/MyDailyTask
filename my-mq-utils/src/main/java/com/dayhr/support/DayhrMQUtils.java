package com.dayhr.support;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chen
 */
public final class DayhrMQUtils {
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String currentTime() {
        return sdf.format(new Date());
    }
}
