package com.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * package com.dayhr.config
 * description
 * Created by chen on 2016/8/31.
 */
public class LoadConfigUtils {
    static Logger LOGGER = LoggerFactory.getLogger(LoadConfigUtils.class);

    static final Properties configProp = PropertiesUtils.configProp;

    static int findIntValueFromProperties(final String prop) {
        return Integer.parseInt(configProp.getProperty(prop));
    }

    static long findLongValueFromProperties(final String prop) {
        return Long.parseLong(configProp.getProperty(prop));
    }

    static boolean findBooleanValueFromProperties(final String prop) {
        return Boolean.parseBoolean(configProp.getProperty(prop));
    }

    public static final <T>String printConfigValue(T printObj) {
        Method[] methods = printObj.getClass().getDeclaredMethods();
        StringBuilder sb = new StringBuilder();
        sb.append("打印配置文件信息:\n");
        try {
            for (int i = 0, len = methods.length; i < len; i++) {
                Method method = methods[i];
                String methodName = method.getName();
                int keyIndex = methodName.indexOf("get") > -1 ? 3 :
                        methodName.indexOf("is") > -1 ? 2 : 0;
                if (keyIndex > 0) {
                    sb.append(String.valueOf(methodName.charAt(keyIndex)).toLowerCase())
                            .append(methodName.substring(++keyIndex))
                            .append("=")
                            .append(method.invoke(printObj, null))
                            .append("\n");
                }
            }
        } catch (Exception e) {
            LOGGER.error("打印{}配置信息失败:当前堆栈信息为：",printObj.getClass(), e);
        }
        return sb.toString();
    }
}
