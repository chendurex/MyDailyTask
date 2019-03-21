package com.jdk.lambad;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 模拟下lambda功能
 * @author cheny.huang
 * @date 2019-03-11 14:38.
 */
public class LambdaInternal<T,R> {
    private List<T> original = new ArrayList<>();
    private List<R> convert = new ArrayList<>();
    private List<Predicate<T>> predicates = new ArrayList<>();
    private LambdaInternal<T,R> map(Function<T, R> function) {
        for (T t : original) {
            out:
            if (!predicates.isEmpty()) {
                for (Predicate<T> pre : predicates) {
                    if (!pre.test(t)) {
                        break out;
                    }
                }
                convert.add(function.apply(t));
            }
        }
        return this;
    }

    private LambdaInternal<T,R> filter(Predicate<T> predicate) {
        predicates.add(predicate);
        return this;
    }

    private List<R> toList(){
        return convert;
    }

    private LambdaInternal<T,R> of(T[] initial) {
        original.addAll(Arrays.asList(initial));
        return this;
    }

    @Test
    public void testMap() {
        List<Integer> newer = new LambdaInternal<String, Integer>().of(new String[]{"1","2", "3"})
                .filter(s->!"1".equals(s))
                .filter(s->!"2".equals(s))
                .map(s->Integer.valueOf(s)*Integer.valueOf(s))
                .toList();
        Assert.assertEquals(Arrays.asList(9), newer);
    }
}
