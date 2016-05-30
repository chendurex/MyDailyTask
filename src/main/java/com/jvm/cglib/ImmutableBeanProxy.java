package com.jvm.cglib;

import net.sf.cglib.beans.ImmutableBean;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/30 9:32
 */
public class ImmutableBeanProxy {
    /**
     * cglib's ImmutableBean allows you to create an immutability wrapper similar to for example Collections#immutableSet.
     * All changes of the underlying bean will be prevented by an IllegalStateException
     * (however, not by an UnsupportedOperationException as recommended by the Java API). Looking at some bean
     * @throws Exception
     */
    @Test
    public void testImmutableBean() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setName("Hello world!");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
        Assert.assertEquals("Hello world!", immutableBean.getName());
        bean.setName("Hello world, again!");
        Assert.assertEquals("Hello world, again!", immutableBean.getName());
        immutableBean.setName("Hello cglib!"); // Causes exception.
    }
}
