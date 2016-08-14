package com.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyCallable {
    @Test
    public void callable() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        MySubCallable<String> sub = new MySubCallable();
        List<Future<String>> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(executorService.submit(sub));
        }
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }

    class MySubCallable<V> implements Callable<V> {
        final AtomicInteger number = new AtomicInteger(20);
        final String prefix = "calculate value :";
        @Override
        public V call() throws Exception {
            TimeUnit.SECONDS.sleep(number.get());
            return (V)(prefix + number.getAndIncrement());
        }
    }
}
