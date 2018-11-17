package com.reflection;

import org.joor.Reflect;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author cheny.huang
 * @date 2018-09-04 10:27.
 */
public class JoorTest {
    /**
     * 调用静态的方法或者静态域
     * 不管方法或者域是否私有
     */
    @Test
    public void testCallStaticMethod() {
        Assert.assertNotNull(Reflect.on(ReflectObj.class).call("boot"));
        Assert.assertNotNull(Reflect.on(ReflectObj.class).get("value"));
    }

    /**
     * 调用实例方法或者实例域
     * 不管方法或者域是否私有
     */
    @Test
    public void testCallInsMethod() {
        // 构建实例对象，不管对象是否含有私有构造函数
        ReflectObj obj = Reflect.on(ReflectObj.class).create("wodiu").get();
        // reflect.on方法传入的是class则调用静态方法，传入的是instance则调用的是实例
        Assert.assertEquals("hehe", Reflect.on(obj).call("say", "hehe").get());
        Assert.assertEquals("wodiu", Reflect.on(obj).get("name"));
        // 如果调用的是void方法，再调用get获取返回值时，返回的是原始对象
        Assert.assertEquals(obj, Reflect.on(obj).call("vm").get());
    }

    @Test
    public void testGenAndSetField() {
        // static filed
        Reflect.on(ReflectObj.class).set("value", "hehe");
        // field equivalent Reflect.on(obj).get
        Assert.assertEquals("hehe", Reflect.on(ReflectObj.class).field("value").get());

        // instance field
        ReflectObj obj = new ReflectObj();
        Reflect.on(obj).set("name", "hehe");
        // field equivalent Reflect.on(obj).get
        Assert.assertEquals("hehe", Reflect.on(obj).field("value").get());
    }

    @Test
    public void getAllFieldAndMethod() {
        System.out.println(Reflect.on(ReflectObj.class).fields());
        System.out.println(Reflect.on(new ReflectObj<>()).fields());
    }
}
