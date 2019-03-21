package com.jdk.lambad.dsl;

import org.junit.Assert;

import java.util.Collection;

/**
 * @author cheny.huang
 * @date 2019-03-19 10:18.
 */
public class Expect {
    private Object t;
    public Expect that(Object t) {
        this.t = t;
        return this;
    }

    public void isEmpty() {
        Assert.assertTrue(((Collection)t).isEmpty());
    }

    public void isEquals(Object t) {
        Assert.assertEquals(this.t,  t);
    }
}
