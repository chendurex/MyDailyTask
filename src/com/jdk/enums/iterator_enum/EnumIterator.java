package com.jdk.enums.iterator_enum;

import java.util.Iterator;

/**
 * Created by LENOVO on 2016/3/16.
 */
public enum  EnumIterator {
    RED,BLUE,GREEN;

    public static void main(String[] args) {
        EnumIterator[] itr =  EnumIterator.values();
        for (EnumIterator e : itr) {
            System.out.println(e);
        }
    }
}
