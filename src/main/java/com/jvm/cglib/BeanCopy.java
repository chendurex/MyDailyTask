package com.jvm.cglib;

import net.sf.cglib.beans.BeanCopier;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/30 9:46
 */
public class BeanCopy {
    /**
     * The BeanCopier is another bean utility that copies beans by their property values. Consider another bean with similar properties as SampleBean
     * without being restrained to a specific type. The BeanCopier#copy method takles an (eventually)
     * optional Converter which allows to do some further manipulations on each bean property. If the BeanCopier
     * is created with false as the third constructor argument, the Converter is ignored and can therefore be null
     * @throws Exception
     */
    @Test
    public void testBeanCopier() throws Exception {
        BeanCopier copier = BeanCopier.create(SampleBean.class, OtherSampleBean.class, false);
        SampleBean bean = new SampleBean();
        bean.setName("Hello cglib!");
        OtherSampleBean otherBean = new OtherSampleBean();
        copier.copy(bean, otherBean,null);
        Assert.assertEquals("Hello cglib!", otherBean.getName());
    }
}
