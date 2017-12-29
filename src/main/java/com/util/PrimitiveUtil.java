package com.util;

import java.math.BigDecimal;

/**
 * @author chen
 * @description 原始类型与包装类型互换以及效验工具
 * @pachage com.dayhr.web.module.hr.time.attendance.common
 * @date 2016/5/5 11:41
 */
public class PrimitiveUtil {
    private PrimitiveUtil() {
        throw new AssertionError();
    }

    /**
     * object 转换为 int 类型
     * 如果为null 则为int 默认值
     * 否则根据类型返回对象的intValue值
     *
     * @param object
     * @return
     */
    public static int objectToInt(Object object) {
        int value = 0;
        if (object != null) {
            if (object instanceof Integer) {
                value = integerToInt((Integer) object);
            } else if (object instanceof String) {
                value = stringToInt(String.valueOf(object));
            } else if (object instanceof Short) {
                value = ((Short) object).intValue();
            } else if (object instanceof Byte) {
                value = ((Byte) object).intValue();
            } else if (object instanceof Float) {
                value = ((Float) object).intValue();
            } else if (object instanceof Double) {
                value = ((Double) object).intValue();
            } else if (object instanceof Long) {
                value = ((Long) object).intValue();
            } else if (object instanceof Boolean) {
                value = ((Boolean) object) ? 1 : 0;
            }
        }
        return value;
    }

    public static boolean isBoolean(Boolean bool) {
        return bool != null && bool;
    }

    public static int booleanToInt(final boolean bool) {
        return bool ? 1 : 0;
    }

    public static boolean stringToBoolean(final String value) {
        return "1".equals(value) || "true".equals(value);
    }

    /**
     * Integer 转换为 int 类型
     * 如果为null 则为int 默认值
     *
     * @param integer
     * @return
     */
    public static int integerToInt(Integer integer) {
        return integer == null ? 0 : integer;
    }

    /**
     * String 转换为int 类型
     * 如果为null 则为int 默认值
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        return str == null ? 0 : integerToInt(Integer.valueOf(str));
    }

    /**
     * 两个integer 类型比较是否相等
     * 如果其中一个为null则不相等，否则按照intValue值比较
     *
     * @param integer1
     * @param integer2
     * @return 相等true，否则false
     */
    public static boolean integerEquals(Integer integer1, Integer integer2) {
        return integerCompare(integer1, integer2) == 0;
    }

    public static boolean longEquals(Long long1, Long long2) {
        return longCompare(long1, long2) == 0;
    }

    public static boolean integerNotEquals(Integer integer1, Integer integer2) {
        return !integerEquals(integer1, integer2);
    }

    /**
     * integer1 与多个integer2比较，如果integer1跟其中的一个integer2相等则返回true
     * 如果integer2 == null 或者 integer2 ==null 或者integer2.length ==0 则返回false
     *
     * @param integer1
     * @param integer2
     * @return
     */
    public static boolean multiIntegerEquals(Integer integer1, Integer... integer2) {
        if (integer1 == null || integer2 == null || integer2.length == 0) {
            return false;
        }
        for (int i = 0, len = integer2.length; i < len; i++) {
            if (integerEquals(integer1, integer2[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 两个integer大小比较
     * 如果其中有一个为null则返回Integer.MIN_VALUE
     *
     * @param integer1
     * @param integer2
     * @return integer1.intValue() - integer2.intValue();
     */
    private static int integerCompare(Integer integer1, Integer integer2) {
        if (integer1 != null && integer2 != null) {
            return integer1 - integer2;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public static long longCompare(Long long1, Long long2) {
        if (long1 != null && long2 != null) {
            return long1 - long2;
        } else {
            return Long.MIN_VALUE;
        }
    }

    /*
     * BigDecimal 比较是否相等
     * 如果其中一个值为null则默认赋值为0
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static boolean bigDecimalEquals(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        return bigDecimalCompare(bigDecimal1, bigDecimal2) == 0;
    }

    /**
     * BigDecimal 比较大小
     * 如果其中一个值为null则默认赋值为0
     * 然后再调用bigDecimal1.compareTo(bigDecimal2)
     *
     * @param bigDecimal1
     * @param bigDecimal2
     * @return
     */
    public static int bigDecimalCompare(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null) {
            bigDecimal1 = BigDecimal.ZERO;
        }
        if (bigDecimal2 == null) {
            bigDecimal2 = BigDecimal.ZERO;
        }
        return bigDecimal1.compareTo(bigDecimal2);
    }

    public static boolean intToBool(Integer value) {
        return value != null && value > 0;
    }

    public static void main(String[] args) {
        System.out.println(intToBool(1));
    }
}
