package com.util;

import java.io.File;

/**
 * @author chen
 * @date 2016/11/13 13:57
 */
public class ClassUtil {

    public static String getClassFile(Class<?> clazz) {
        return getClassFile(clazz.getName());
    }

    public static String getClassFile(String path) {
        return new StringBuilder()
                .append(path.substring(path.lastIndexOf(".") + 1))
                .append(".class")
                .toString();
    }

    public static String dotPathToSlantPath(Class<?> clazz) {
        return  dotPathToSlantPath(clazz, File.separator);
    }

    public static String dotPathToSlantPath(Class<?> clazz, String symbol) {
        return  clazz.getName().replace(".", symbol);
    }

    public static void main(String[] args) {
        System.out.println(getClassFile("com.jvm.asm.demo.InspectionCodeTest"));
        System.out.println(dotPathToSlantPath(ClassUtil.class, "/"));
    }
}
