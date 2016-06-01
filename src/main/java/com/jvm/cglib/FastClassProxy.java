package com.jvm.cglib;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import net.sf.cglib.reflect.FastMethod;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/6/1 9:36
 */
public class FastClassProxy {
    /**
     * Besides the demonstrated FastMethod, the FastClass can also create FastConstructors but no fast fields.
     * But how can the FastClass be faster than normal reflection? Java reflection is executed by JNI where method invocations are executed by some C-code.
     * The FastClass on the other side creates some byte code that calls the method directly from within the JVM.
     * However, the newer versions of the HotSpot JVM (and probably many other modern JVMs) know a concept called inflation
     * where the JVM will translate reflective method calls into native version's of FastClass when a reflective method is executed often enough.
     * You can even control this behavior (at least on a HotSpot JVM) with setting the sun.reflect.inflationThreshold property to a lower value.
     * (The default is 15.) This property determines after how many reflective invocations a JNI call should be substituted by a byte code instrumented version.
     * I would therefore recommend to not use FastClass on modern JVMs, it can however fine-tune performance on older Java virtual machines.
     * @throws Exception
     */
    @Test
    public void testFastClass() throws Exception {
        FastClass fastClass = FastClass.create(SampleBean.class);
        FastMethod fastMethod = fastClass.getMethod(SampleBean.class.getMethod("getValue"));
        SampleBean myBean = new SampleBean();
        myBean.setValue("Hello cglib!");
        Assert.assertEquals("Hello cglib!",fastMethod.invoke(myBean, new Object[0]));

        FastConstructor fastConstructor = fastClass.getConstructor(new Class[]{});
        Object sb =  fastConstructor.newInstance();
        Assert.assertTrue(sb.getClass().isAssignableFrom(SampleBean.class));
    }
}
