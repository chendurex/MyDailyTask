package com.dayhr.support;

import java.util.Map;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.support
 * @date 2016/8/31 11:15
 */
public final class DayhrMQ {
    /**
     * 发送普通的pojo对象
     * 适用场景：一个交互式的java对象
     * @param t
     * @param <T>
     */
    public static <T> void sendObject(T t) {}

    /**
     * 发送Map集合
     * 适用场景：Map的键值对
     * @param mapValue
     * @param <K>
     * @param <V>
     */
    public static <K,V> void sendMap(Map<K,V> mapValue) {}

    /**
     * 发送任何类型
     * 适用场景：普通的一个字符串、list集合、文本对象
     * @param text
     */
    public static void sendText(String text) {}
}
