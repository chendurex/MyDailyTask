package com.jvm.cglib;

import net.sf.cglib.beans.BeanMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/31 9:06
 */
public class BeanMapProxy {
    /**
     * This is the last bean utility within the cglib library. The BeanMap converts all properties of a bean to a String-to-Object Java Map:
     * Additionally, the BeanMap#newInstance(Object) method allows to create maps for other beans by reusing the same Class
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        SampleBean bean = new SampleBean();
        BeanMap map = BeanMap.create(bean);
        bean.setValue("Hello cglib!");
        Assert.assertEquals("Hello cglib", map.get("value"));
    }
}
