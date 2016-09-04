package com.dayhr.jms;

import com.dayhr.config.DayhrDefaultConnectionConfig;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public interface DayhrConnectionFactory {
    DayhrConnection createConnection();
    DayhrConnection createConnection(DayhrDefaultConnectionConfig config);
}
