package com.algorithms;

import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MyQueue<T> {
    @Test
    public void test() {
        MyQueue<Integer> queue = new MyQueue<>();
        for (int i = 0 ;i < 200; i++) {
            queue.enqueue(i);
            System.out.println(queue.dequeue());
        }
    }

    private Object [] table = new Object[20];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public boolean enqueue(T t) {
        if (isFull()) {
            throw new IllegalStateException("MyCallable full");
        }
        table[tail++] = t;
        if (tail == table.length) {
            tail = 0;
        }
        size++;
        return true;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object obj = table[head++];
        if (head == table.length) {
            head = 0;
        }
        size--;
        return (T)obj;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == table.length;
    }
}
