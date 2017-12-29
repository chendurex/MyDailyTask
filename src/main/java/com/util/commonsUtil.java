package com.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chen
 *         2017/12/29 14:26
 */
public class commonsUtil {
    public static final Pattern PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 判断是否包含中文
     * @param str     字符串
     * @return        boolean
     */
    public static boolean isContainChinese(String str) {
        return PATTERN.matcher(str).find();
    }

    /**
     * 判断字符串中是否包含中文符号
     *
     * @param value
     * @return
     */
    public static boolean hasChinesePunctuation(String value) {
        if (value == null) {
            return false;
        }
        for (char c : value.toCharArray()) {
            if (isChinesePunctuation(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断中文符号
     *
     * @param c
     * @return
     */
    private static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return chineseUnicodeBlock.contains(ub);
    }

    private final static List<Character.UnicodeBlock> chineseUnicodeBlock = Arrays.asList(
            Character.UnicodeBlock.GENERAL_PUNCTUATION,
            Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION,
            Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,
            Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS,
            Character.UnicodeBlock.VERTICAL_FORMS);

    /**
     * 驼峰式的字段转换为下划线类型字段
     *
     * @param field
     * @return
     */
    public static String javaFieldToTableField(String field) {
        char[] chars = field.toCharArray();
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
     * 表字段转换为java属性
     *
     * @param field
     * @return
     */
    public static String tableFieldToJavaField(String field) {
        StringBuilder sb = new StringBuilder(field.length());
        String[] sub = field.trim().split("_");
        sb.append(sub[0]);
        for (int j = 1; j < sub.length; j++) {
            sb.append(sub[j].substring(0, 1).toUpperCase()).append(sub[j].substring(1));
        }
        return sb.toString();
    }

    /**
     * null对象备份<br>
     * 如果t1等于空则返回t2，否则返回t1<br>
     * 此功能用于if (xxx == null) ? yyy : xxx; 场景
     *
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> T nullBackup(T t1, T t2) {
        return t1 == null ? t2 : t1;
    }

    public static <T> T emptyBackup(T t1, T t2) {
        return isNotEmpty(t1) ? t1 : t2;
    }

    public static <T> T emptyBackup(T t1, T t2, T t3, T t4) {
        if (isNotEmpty(t1)) {
            return t1;
        } else if (isNotEmpty(t2)) {
            return t2;
        } else if (isNotEmpty(t3)) {
            return t3;
        } else {
            return t4;
        }
    }

    /**
     * 判断object是否为空，包括null和空字符串
     * 如果object为空则返回true，否则返回false
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(final Object object) {
        return object == null || "".equals(object);
    }

    /**
     * 检查对象是否不为空<br>
     * 如果为空则返回false，否则返回true
     *
     * @param object
     * @return
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }
}
