package com.jvm.cglib;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.InterfaceMaker;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Type;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/31 9:40
 */
public class InterfaceMakerProxy {
    @Test
    public void testInterfaceMaker() throws Exception {
        Signature signature = new Signature("foo", Type.DOUBLE_TYPE, new Type[]{Type.INT_TYPE});
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(signature, new Type[0]);
        Class iface = interfaceMaker.create();
        Assert.assertEquals(1, iface.getMethods().length);
        Assert.assertEquals("foo", iface.getMethods()[0].getName());
        Assert.assertEquals(double.class, iface.getMethods()[0].getReturnType());
    }

    @Test
    public void typeCreate() {
        Type [] t = new Type[]{Type.getType(String.class),Type.INT_TYPE};

        System.out.println(t[1]);
    }
}
