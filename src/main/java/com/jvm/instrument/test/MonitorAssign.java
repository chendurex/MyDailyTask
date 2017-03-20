package com.jvm.instrument.test;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;


/**
 * Created by chen on 2016/5/28.
 * VirtualMachine 是java tools.jar提供的可以动态绑定某个jvm实例
 * VirtualMachine.attach(String args);传入一个jvm实例的pid
 * 然后再执行vm.loadAgent(String args); 传入一个代理类的路径如：d:\\project\javaagent.jar
 * vm.detach() 释放资源
 */
public class MonitorAssign {
    public  static  void  main(String[] args)throws  InterruptedException {
        try {
            VirtualMachine vm = VirtualMachine.attach(args[0]);

            List<VirtualMachineDescriptor> virtualMachineDescriptor = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : virtualMachineDescriptor) {
                System.out.println(vmd);
            }
            vm.loadAgent(args[1]);
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
