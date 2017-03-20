package com.jvm.cglib.method.delegate;

import com.jvm.cglib.SampleBean;
import net.sf.cglib.reflect.MethodDelegate;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib.method_delegate
 * @date 2016/6/1 9:15
 */
public class MethodDelegateProxy {
    /**
     * A MethodDelegate allows to emulate a C#-like delegate to a specific method by binding a method call to some interface.
     * For example, the following code would bind the SampleBean#getValue method to a delegate:
     * There are however some things to note:

     The factory method MethodDelegate#create takes exactly one method name as its second argument.
     This is the method the MethodDelegate will proxy for you.
     There must be a method without arguments defined for the object which is given to the factory method as its first argument.
     Thus, the MethodDelegate is not as strong as it could be.
     The third argument must be an interface with exactly one argument. The MethodDelegate implements this interface and can be cast to it.
     When the method is invoked, it will call the proxied method on the object that is the first argument.
     * @throws Exception
     */
    @Test
    public void testMethodDelegate() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(
                bean, "getValue", BeanDelegate.class);
        Assert.assertEquals("Hello world!", delegate.getValueFromDelegate());
    }
}
