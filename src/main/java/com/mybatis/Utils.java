package com.mybatis;

import com.google.common.base.Predicate;
import org.reflections.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author chen
 * @description
 * @pachage com.mybatis
 * @date 2016/12/23 10:25
 */
public class Utils {
    public static final String nextLine = "\r\n";
    public static final String nextSpace = "\r\n    ";
    private static final int SPLIT = 100;

    /**
     * java属性值组装成mapper使用的属性值
     * @param value
     * @return
     */
    public static String tableFieldToMapperFiled(String value) {
        List<String> fields = Arrays.asList(value.split(","));
        StringBuilder sb = new StringBuilder(value.length());
        int split = SPLIT;
        for (String field : fields) {
            sb.append("#{").append(field).append("}, ");
            if (sb.length() > split) {
                sb.append(nextSpace);
                split += SPLIT;
            }
        }
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * 表字段转换为java属性
     * @param field
     * @return
     */
    public static String tableFieldToJavaField(String field) {
        StringBuilder sb = new StringBuilder(field.length());
        String sub [] = field.trim().split("_");
        sb.append(sub[0]);
        for (int j = 1; j < sub.length; j++) {
            sb.append(sub[j].substring(0,1).toUpperCase()).append(sub[j].substring(1));
        }
        return sb.toString();
    }

    /**
     * 将整个类的java字段转换为表字段，多个属性以逗号隔开
     * @param clazz
     * @return
     */
    public static String javaFieldToTableField(Class<?> clazz) {
        Set<Field> fields = getAllFields(clazz);
        StringBuilder sb = new StringBuilder(fields.size() * 10);
        int split = SPLIT;
        for (Field field : fields) {
            sb.append(",").append(TypeAlias.getField(clazz, field.getName()));
            if (sb.length() > split) {
                sb.append(nextSpace);
                split += SPLIT;
            }
        }
        return sb.substring(1);
    }

    /**
     * java属性转换为表字段
     * @param field
     * @return
     */
    public static String javaFieldToTableField(String field) {
        char [] chars = field.toCharArray();
        StringBuilder sb = new StringBuilder(chars.length);
        for (int i = 0, len = chars.length; i < len; i++) {
            char c = chars[i];
            if (i == 0 && Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.isUpperCase(c) ? "_" + Character.toLowerCase(c) : c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取所有的java属性字段并且以逗号隔开
     * @param clazz
     * @return
     */
    public static String getAllFieldsAndConvert(Class<?> clazz) {
        Set<Field> fields = getAllFields(clazz, Collections.<String>emptyList());
        StringBuilder sb = new StringBuilder(fields.size() * 10);
        for (Field field : fields) {
            sb.append(",").append(field.getName());
        }
        return sb.substring(1);
    }

    public static Set<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, Collections.<String>emptyList());
    }

    /**
     * 获取某个类的所有字段
     * @param clazz 类名
     * @param exclude 排除的字段名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Set<Field> getAllFields(Class<?> clazz, final List<String> exclude) {
        return ReflectionUtils.getAllFields(clazz, new Predicate<Field>() {
            @Override
            public boolean apply(Field input) {
                return !exclude.contains(input.getName());
            }
        });
    }
}
