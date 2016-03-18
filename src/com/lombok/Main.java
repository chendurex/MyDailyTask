package com.lombok;

import com.lombok.bean.LombokType;

import java.math.BigDecimal;

/**
 * Created by LENOVO on 2016/3/17.
 */
public class Main {
    public static void main(String[] args) {
        LombokType lombok = new LombokType();
        lombok.setByte_(Byte.MAX_VALUE);lombok.setByte__(Byte.MIN_VALUE);
        lombok.setShort_(Short.MAX_VALUE);lombok.setShort__(Short.MIN_VALUE);
        lombok.setInteger_(Integer.MAX_VALUE);lombok.setInt__(Integer.MIN_VALUE);
        lombok.setLong_(Long.MAX_VALUE);lombok.setLong__(Long.MIN_VALUE);
        lombok.setFloat_(Float.MAX_VALUE);lombok.setFloat__(Float.MIN_VALUE);
        lombok.setDouble_(Double.MAX_VALUE);lombok.setDouble__(Double.MIN_VALUE);
        lombok.setBoolean_(Boolean.TRUE);lombok.setBoolean__(Boolean.FALSE);
        lombok.setCharacter_(Character.MAX_VALUE);lombok.setChar__(Character.MIN_VALUE);
        lombok.setBigDecimal(BigDecimal.TEN);

        System.out.println("byte ->"+lombok.getByte_()+"---"+lombok.getByte__());
        System.out.println("short ->"+lombok.getShort_()+"---"+lombok.getShort__());
        System.out.println("int ->"+lombok.getInteger_()+"---"+lombok.getInt__());
        System.out.println("long ->"+lombok.getLong_()+"---"+lombok.getLong__());
        System.out.println("float ->"+lombok.getFloat_()+"---"+lombok.getFloat__());
        System.out.println("double ->"+lombok.getDouble_()+"---"+lombok.getDouble__());
        System.out.println("char ->"+lombok.getCharacter_()+"---"+lombok.getChar__());
        System.out.println("boolean ->"+lombok.getBoolean_()+"---"+lombok.getBoolean_());
        System.out.println("bigDecimal ->"+lombok.getBigDecimal());

        System.out.println(LombokType.builder().bigDecimal(BigDecimal.TEN).Double_(Double.MAX_VALUE).build());
    }
}
