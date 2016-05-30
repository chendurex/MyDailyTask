package com.jvm.cglib;

import net.sf.cglib.beans.BeanGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/30 9:41
 */
public class BeanGeneratorProxy {
    /**
     * This might be useful when another library expects beans which it resolved by reflection but you do not know these beans at run time.
     * (An example would be Apache Wicket which works a lot with beans.)
     * @throws Exception
     */
    @Test
    public void testBeanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object myBean = beanGenerator.create();

        Method setter = myBean.getClass().getMethod("setValue", String.class);
        setter.invoke(myBean, "Hello cglib!");
        Method getter = myBean.getClass().getMethod("getValue");
        Assert.assertEquals("Hello cglib!", getter.invoke(myBean));
    }
}
