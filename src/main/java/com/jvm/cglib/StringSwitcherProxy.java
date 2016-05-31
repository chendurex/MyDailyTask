package com.jvm.cglib;

import net.sf.cglib.util.StringSwitcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/31 9:34
 */
public class StringSwitcherProxy {
    /**
     * The StringSwitcher allows to emulate a switch command on Strings such as it is possible with the built-in Java switch statement since Java 7.
     * If using the StringSwitcher in Java 6 or less really adds a benefit to your code remains however doubtful and I would personally not recommend its use
     * @throws Exception
     */
    @Test
    public void testStringSwitcher() throws Exception {
        String[] strings = new String[]{"one", "two"};
        int[] values = new int[]{10, 20};
        StringSwitcher stringSwitcher = StringSwitcher.create(strings, values, true);
        Assert.assertEquals(10, stringSwitcher.intValue("one"));
        Assert.assertEquals(20, stringSwitcher.intValue("two"));
        Assert.assertEquals(-1, stringSwitcher.intValue("three"));
    }
}
