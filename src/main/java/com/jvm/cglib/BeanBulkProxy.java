package com.jvm.cglib;

import net.sf.cglib.beans.BulkBean;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/31 9:02
 */
public class BeanBulkProxy {
    /**
     * A BulkBean allows to use a specified set of a bean's accessors by arrays instead of method calls:
     * The BulkBean takes an array of getter names, an array of setter names and an array of property types as its constructor arguments.
     * The resulting instrumented class can then extracted as an array by BulkBean#getPropertyValues(Object).
     * Similarly, a bean's properties can be set by BulkBean#setPropertyValues(Object, Object[]).
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        BulkBean bulkBean = BulkBean.create(SampleBean.class,
                new String[]{"getValue"},
                new String[]{"setValue"},
                new Class[]{String.class});
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world!");
        Assert.assertEquals(1, bulkBean.getPropertyValues(bean).length);
        Assert.assertEquals("Hello world!", bulkBean.getPropertyValues(bean)[0]);
        bulkBean.setPropertyValues(bean, new Object[] {"Hello cglib!"});
        Assert.assertEquals("Hello cglib!", bean.getValue());
    }
}
