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
        /*for (int i = 0; i < 20; i++) {
            System.out.println(queue.dequeue());
        }*/
    }

    private Object [] table = new Object[20];
    private int head = 0;
    private int tail = 0;

    public void enqueue(T t) {
        if (isFull()) {
             throw new IllegalStateException("MyQueue full");
        }
        final int curTail = tail;
        final int len = table.length;
        if (curTail == len) {
            tail = 0;
        }
        table[tail ++] = t;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object obj = table[head ++];
        final int curHead = head;
        final int len = table.length;
        if (curHead == len) {
            head = 0;
        }
        return (T)obj;
    }

    public boolean isFull() {
         if (tail + 1 == head) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return tail == head;
    }
}
