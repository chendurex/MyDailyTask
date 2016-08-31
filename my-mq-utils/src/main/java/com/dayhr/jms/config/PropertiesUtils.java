package com.dayhr.jms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * package com.dayhr.jms.config
 * description
 * Created by chen on 2016/8/31.
 */
class PropertiesUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    static final Properties configProp = new Properties();
    static {
        try {
            configProp.load(PropertiesUtils.class.getResourceAsStream("DayhrMQProperties"));
        } catch (IOException e) {
            LOGGER.error("无法加载DayhrMQProperties属性文件，堆栈信息为：", e);
        }
    }

}
