package com.algorithms;

import org.junit.Test;

import java.util.EmptyStackException;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MyStack<T> {
    @Test
    public void test() {
        MyStack<Integer> stack = new MyStack<>();
        for (int i = 0 ;i < 20; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 20; i++) {
            System.out.println(stack.pop());
        }
    }
    private Object[] table = new Object[20];
    private int elementCount = 0;

    public void push(T t) {
        if (isFull()) {
            throw new RuntimeException("堆已经满了，不能再放入数据");
        }
        table[elementCount ++] = t;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T)table[-- elementCount];
    }

    public boolean isEmpty() {
        return elementCount == 0;
    }

    public boolean isFull() {
        return table.length == elementCount;
    }
}
