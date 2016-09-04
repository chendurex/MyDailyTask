package com.dayhr.jms;

import com.dayhr.config.DayhrUserConnectionConfig;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:05
 */
public interface DayhrConnection {
    void setUserConfig(DayhrUserConnectionConfig userConfig);
}
