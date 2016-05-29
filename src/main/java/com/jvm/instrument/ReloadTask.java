package com.jvm.instrument;

/**
 * Created by Administrator on 2016/5/28.
 */

import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.TimerTask;

public class ReloadTask extends TimerTask {
    private Instrumentation inst;

    protected ReloadTask(Instrumentation inst) {
        this.inst = inst;
    }

    /**
     * 定时的获取当前jvm实例被jvm加载的所有类，然后监控自己定义的类
     * 如果支持重新加载类则执行redefineClasses类
     * 如果支持转换类则执行retransformClasses
     */
    @Override
    public void run() {
        try {
            ClassDefinition[] cd = new ClassDefinition[1];
            Class[] classes = inst.getAllLoadedClasses();
            for (Class cls : classes) {
                if (cls.getClassLoader() == null ||
                        !cls.getClassLoader().getClass().getName().equals("sun.misc.Launcher$AppClassLoader")
                        || !cls.getName().contains("com.jvm"))
                    continue;
                System.out.println("当前已经被加载的类===" + cls.getName());
                if (inst.isRedefineClassesSupported()) {
                    String name = cls.getName().replaceAll("\\.", "/");
                    cd[0] = new ClassDefinition(cls, loadClassBytes(cls, name + ".class"));
                    inst.redefineClasses(cd);
                } else if (inst.isRetransformClassesSupported()) {
                    inst.retransformClasses(cls);
                } else {
                    // do noting
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] loadClassBytes(Class cls, String clsname) throws Exception {
        System.out.println("当前加载的类名称："+clsname+"====class对象是："+cls);
        InputStream is = cls.getClassLoader().getSystemClassLoader().getResourceAsStream(clsname);
        if (is == null) return null;
        byte[] bt = new byte[is.available()];
        is.read(bt);
        is.close();
        return bt;
    }
}

