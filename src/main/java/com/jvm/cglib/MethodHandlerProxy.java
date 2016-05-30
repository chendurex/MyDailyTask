package com.jvm.cglib;

import net.sf.cglib.proxy.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/26 9:46
 */
public class MethodHandlerProxy {


    /**
     * 所有的方法都是返回同一个值
     * 注意，因为这里返回的是是Object对象，但是实际填充的是任意值会导致类型转换失败
     */
    @Test
    public void testFixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello enhancer test!";
            }
        });
        SampleBean sampleBean = (SampleBean) enhancer.create();
        Assert.assertEquals("Hello cglib!", sampleBean.test("hello cglib!"));
    }

    /**
     * his callback allows us to answer with regards to the invoked method. However,
     * you should be careful when calling a method on the proxy object that comes with the InvocationHandler#invoke method.
     * All calls on this method will be dispatched with the same InvocationHandler and might therefore result in an endless loop.
     * In order to avoid this, we can use yet another callback dispatcher:
     */
    @Test
    public void testInvocationHandler() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "invocation method handle";
                } else {
                    throw new Exception("nothing to do");
                }

            }
        });
        SampleBean sampleBean = (SampleBean) enhancer.create();
        Assert.assertEquals("invocation method handle", sampleBean.test(null));
        Assert.assertEquals("test", sampleBean.hashCode());
    }

    /**
     * The MethodInterceptor allows full control over the intercepted method and offers some utilities
     * for calling the method of the enhanced class in their original state. But why would one want to
     * use other methods anyways? Because the other methods are more efficient and cglib is often used in edge case
     * frameworks where efficiency plays a significant role. The creation and linkage of the MethodInterceptor
     * requires for example the generation of a different type of byte code and the creation of some runtime objects
     * that are not required with the InvocationHandler
     */
    @Test
    public void MethodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "method interceptor test";
                } else {
                    return proxy.invokeSuper(obj,args);
                    //return method.invoke(obj,args);
                }
            }
        });
        SampleBean sampleBean = (SampleBean)enhancer.create();
        Assert.assertEquals(sampleBean.test("method interceptor test"),"method interceptor test");
        System.out.println(sampleBean.hashCode());
    }

    /**
     * Even though the LazyLoader's only method has the same method signature as FixedValue,
     * the LazyLoader is fundamentally different to the FixedValue interceptor.
     * The LazyLoader is actually supposed to return an instance of a subclass of the enhanced class.
     * This instance is requested only when a method is called on the enhanced object and then stored for
     * future invocations of the generated proxy. This makes sense if your object is expensive in
     * its creation without knowing if the object will ever be used.
     * Be aware that some constructor of the enhanced class must be called both for the proxy object and for the lazily loaded object.
     * Thus, make sure that there is another cheap (maybe protected) constructor available or use an interface type for the proxy.
     * You can choose the invoked constructed by supplying arguments to Enhancer#create(Object...)
     */
    @Test
    public void testLazyLoader() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new LazyLoader() {
            @Override
            public Object loadObject() throws Exception {
                return new SampleBean();
            }
        });
        SampleBean sampleBean = (SampleBean)enhancer.create();
        sampleBean.setName("chen");
        System.out.println(sampleBean.test("test"));


    }

    /**
     * The Dispatcher is like the LazyLoader but will be invoked on every method call without storing the loaded object.
     * This allows to change the implementation of a class without changing the reference to it. Again,
     * be aware that some constructor must be called for both the proxy and the generated objects.
     */
    @Test
    public void testDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new Dispatcher() {
            @Override
            public Object loadObject() throws Exception {
                return new SampleBean();
            }
        });
        SampleBean sampleBean = (SampleBean)enhancer.create();
        sampleBean.setName("chen");
        System.out.println(sampleBean.test("test"));

    }

    /**
     * This class carries a reference to the proxy object it is invoked from in its signature.
     * This allows for example to delegate method calls to another method of this proxy.
     * Be aware that this can easily cause an endless loop and will always cause an endless loop
     * if the same method is called from within ProxyRefDispatcher#loadObject(Object)
     */
    @Test
    public void testProxyRefDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new ProxyRefDispatcher() {
            @Override
            public Object loadObject(Object proxy) throws Exception {
                return null;
            }
        });
        SampleBean sampleBean = (SampleBean)enhancer.create();
        sampleBean.setName("chen");
        System.out.println(sampleBean.test("test"));

    }

    /**
     * 返回CGLIB生成的单例对象
     */
    @Test
    public void testNoOp() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new NoOp() {});
        SampleBean sampleBean = (SampleBean)enhancer.create();
        System.out.println(sampleBean);
        sampleBean.setName("chen");
        System.out.println(sampleBean.test("test"));
    }
}
