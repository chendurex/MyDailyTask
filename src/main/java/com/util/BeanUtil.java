package com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chen
 * @description
 * @date 2016/8/2 15:31
 */
public final class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {
        throw new AssertionError();
    }



    /**
     * 复制bean对象
     *
     * @param dest
     * @param origin
     */
    public static void copyBeans(Object dest, Object origin) {
        try {
            BeanUtils.copyProperties(origin, dest);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static <T> T copyBeans(Class<T> dest, Object origin) {
        try {
            T t = dest.newInstance();
            BeanUtils.copyProperties(origin, t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException("复制对象失败，当前堆栈信息为：", e);
        }
    }

    /**
     * 复制集合里面的对象
     *
     * @param destCls
     * @param origin
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(Class<T> destCls, List<?> origin) {
        if (origin == null || origin.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> copy = new ArrayList<>(origin.size());
        for (Object obj : origin) {
            try {
                T t = destCls.newInstance();
                copyBeans(t, obj);
                copy.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return copy;
    }

    /**
     * 递归打印所有对象内容
     *
     * @param obj
     * @return
     */
    public static String deepPrintObject(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static String beanFieldConvertSQLValue(Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0, len = objects.length; i < len; i++) {
            sb.append(",").append(convertFieldType(objects[i]));
        }
        sb.append(")");
        return sb.deleteCharAt(1).toString();
    }

    private static Object convertFieldType(Object type) {
        if (type instanceof String) {
            return "'" + type + "'";
        } else {
            return type;
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static <T> T parser(InputStream in, Type type) throws IOException {
        return JSON.parseObject(in, type);
    }
}
