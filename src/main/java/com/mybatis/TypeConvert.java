package com.mybatis;

/**
 * @author chen
 * @description
 * @pachage com.mybatis
 * @date 2016/12/23 9:45
 */
public class TypeConvert {
    public static Class<?> getType(final Class<?> c) {
        if (c.isPrimitive()) {
            if (c == Integer.TYPE) {
                return Integer.class;
            } else if (c == Void.TYPE) {
                return Void.class;
            } else if (c == Boolean.TYPE) {
                return Boolean.class;
            } else if (c == Byte.TYPE) {
                return Byte.class;
            } else if (c == Character.TYPE) {
                return Character.class;
            } else if (c == Short.TYPE) {
                return Short.class;
            } else if (c == Double.TYPE) {
                return Double.class;
            } else if (c == Float.TYPE) {
                return Float.class;
            } else if (c == Long.TYPE) {
                return Long.class;
            } else {
                return c;
            }
        } else {
            return c;
        }
    }
}
