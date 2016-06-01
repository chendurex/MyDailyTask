package com.jvm.cglib.constructor;

import com.jvm.cglib.SampleBean;
import net.sf.cglib.reflect.ConstructorDelegate;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib.constructor
 * @date 2016/6/1 9:27
 */
public class ConstructorDelegateProxy {
    /**
     * A ConstructorDelegate allows to create a byte-instrumented factory method.
     * For that, that we first require an interface with a single method newInstance which returns an Object
     * and takes any amount of parameters to be used for a constructor call of the specified class. For example,
     * in order to create a ConstructorDelegate for the SampleBean,
     * we require the following to call SampleBean's default (no-argument) constructor:
     * @throws Exception
     */
    @Test
    public void testConstructorDelegate() throws Exception {
        SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate)
                        ConstructorDelegate.create(SampleBean.class, SampleBeanConstructorDelegate.class);
        SampleBean bean = (SampleBean) constructorDelegate.newInstance();
        Assert.assertTrue(SampleBean.class.isAssignableFrom(bean.getClass()));
    }
}
