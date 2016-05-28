package com.jdk.proxy;

/**
 * @author chen
 * @description TODO
 * @pachage com.jdk.proxy
 * @date 2016/5/24 20:11
 */
public class ComparableTest implements Comparable<String> {
    private String string;
    public ComparableTest(String string) {
        this.string = string;
    }
    @Override
    public int compareTo(String o) {
        return string.compareTo(o);
    }
}
