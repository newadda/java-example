package org.onecell.spring.template.db.utils;

import java.time.Duration;
import java.time.temporal.Temporal;

public class DurationUtil {
    public static boolean isOver(Temporal start, Temporal end, Duration period)
    {
        boolean isOver = false;

        Duration between = Duration.between(start, end);

        if(between.compareTo(period)>0 || between.isNegative()) {
            return true;
        }else{
            return false;
        }
    }
}
