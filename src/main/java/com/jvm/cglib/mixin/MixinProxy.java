package com.jvm.cglib.mixin;

import net.sf.cglib.proxy.Mixin;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib.mixin
 * @date 2016/5/31 9:29
 */
public class MixinProxy {
    /**
     * Some might already know the concept of the Mixin class from other programing languages such as Ruby or Scala (where mixins are called traits).
     * cglib Mixins allow the combination of several objects into a single object. However, in order to do so, those objects must be backed by interfaces:
     * Admittedly, the Mixin API is rather awkward since it requires the classes used for a mixin to implement some interface such that the problem could also be solved by non-instrumented Java.
     * @throws Exception
     */
    @Test
    public void testMixin() throws Exception {
        Mixin mixin = Mixin.create(new Class[]{Interface1.class, Interface2.class,
                MixinInterface.class}, new Object[]{new Class1(), new Class2()});
        MixinInterface mixinDelegate = (MixinInterface) mixin;
        Assert.assertEquals("first", mixinDelegate.first());
        Assert.assertEquals("second", mixinDelegate.second());
    }
}
