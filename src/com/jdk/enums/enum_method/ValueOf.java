package com.jdk.enums.enum_method;

/**
 * Created by LENOVO on 2016/3/17.
 */
public class ValueOf {
    private enum Currency {USD,AUD,RMB};

    public static void main(String[] args) {
        Enum.valueOf(Currency.class,"RMB");
        System.out.println(Currency.values());
        //invalid
        Currency.valueOf("ABD");

    }
}
