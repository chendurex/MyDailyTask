package com.classloader;

import java.io.*;

/**
 * @author chen
 * @description
 * @pachage com.classloader
 * @date 2016/4/28 9:20
 */
public class ClassloaderDemo extends ClassLoader {
    public static final String classUrl = "D:\\dayhr_project\\MyDailyTask\\target\\classes\\com\\classloader\\ClassloaderSub.class";

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.contains("ClassloaderSub")) {
            try {
                InputStream is = new FileInputStream(new File(classUrl));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int data;
                while ((data = is.read()) != -1) {
                    bos.write(data);
                }
                is.close();
                byte [] buf = bos.toByteArray();
                return defineClass(name,buf,0,buf.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.loadClass(name);
    }



    public void newInstance() {
        ClassloaderDemo findClassloader =  new ClassloaderDemo();
        try {
            Class<?> cls = findClassloader.loadClass("com.classloader.ClassloaderSub");
            ClassloaderObj obj = (ClassloaderObj)cls.newInstance();
            obj.setName("hah");
            System.out.println(obj.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClassloaderDemo findClassloader = new ClassloaderDemo();
        findClassloader.newInstance();
    }

}
