package com.jdk.mbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author cheny.huang
 * @date 2019-04-30 09:49.
 */
public class MBeanBoot {
    /* For simplicity, we declare "throws Exception".  Real programs
       will usually want finer-grained exception handling.  */
    public static void main(String[] args) throws Exception {
        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        // Create the Hello World MBean
        HelloMBean mbean = new Hello();
        // Construct the ObjectName for the MBean we will register
        // 如果有多个实例，则以id作为分隔符
        ObjectName name = new ObjectName("com.to8to.tutorial.mbean:type=Hello,id="+System.identityHashCode(mbean));
        HelloMBean mbean2 = new Hello();
        ObjectName name2 = new ObjectName("com.to8to.tutorial.mbean:type=Hello,id="+System.identityHashCode(mbean2));
        // Register the Hello World MBean
        mbs.registerMBean(mbean, name);
        mbs.registerMBean(mbean2, name2);

        // Wait forever
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
