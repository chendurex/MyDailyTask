package com.jvm.btrace;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/5/2.
 */
public class Main {
    private static BtraceData btraceData;
    private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String []args) throws Exception{
        for(;;) {
            setName("chen:"+sdf.format(new Date()));
            getName();
            TimeUnit.SECONDS.sleep(10);
            setBtraceData(btraceData);
            System.out.println(btraceData.toString());
        }
    }

    public static void setBtraceData(BtraceData btraceData) {
        Main.btraceData.setThis(btraceData);
    }
    public static BtraceData getBtraceData() {
        return btraceData;
    }

    public static void checkBtraceData() {
        if (btraceData == null) {
            btraceData = new BtraceData();
        }
    }
    public static void setName(String name) {
        checkBtraceData();
        btraceData.setName(name);
    }

    public static String getName() {
        checkBtraceData();
        return btraceData.getName();
    }

    public static void setGender(String gender) {
        checkBtraceData();
        btraceData.setGender(gender);
    }

    public static void setAge(Integer age) {
        checkBtraceData();
        btraceData.setAge(age);
    }
}
