package com.jvm.cglib;

import net.sf.cglib.core.KeyFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/31 9:14
 */
public class KeyFactoryProxy {
    /**
     * The KeyFactory factory allows the dynamic creation of keys that are composed of multiple values that can be used in for example Map implementations.
     * For doing so, the KeyFactory requires some interface that defines the values that should be used in such a key.
     * This interface must contain a single method by the name newInstance that returns an Object. For example:
     * The KeyFactory will assure the correct implementation of the Object#equals(Object)
     * and Object#hashCode methods such that the resulting key objects can be used in a Map or a Set.
     * The KeyFactory is also used quite a lot internally in the cglib library.
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        SampleKeyFactory keyFactory = (SampleKeyFactory) KeyFactory.create(SampleKeyFactory.class);
        Object key = keyFactory.newInstance("foo", 42);
        Map<Object, String> map = new HashMap<>();
        map.put(key, "Hello cglib!");
        Assert.assertEquals("Hello cglib!", map.get(keyFactory.newInstance("foo", 42)));
    }
}
