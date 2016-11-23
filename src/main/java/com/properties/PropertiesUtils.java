package com.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * package com.dayhr.config
 * description
 * Created by chen on 2016/8/31.
 */
class PropertiesUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    static final Properties configProp = new Properties();
    static {
        try {
            configProp.load(PropertiesUtils.class.getResourceAsStream("/config.properties"));
        } catch (Throwable e) {
            LOGGER.error("无法加载config.properties属性文件，堆栈信息为：", e);
        }
    }

}
