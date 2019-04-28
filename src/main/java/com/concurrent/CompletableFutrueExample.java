package com.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author cheny.huang
 * @date 2019-03-21 11:00.
 */
@Slf4j
public class CompletableFutrueExample {
    @Test
    public void testComposeTasks() {
        long s = System.currentTimeMillis();
        // 异步组合请求
        CompletableFuture.supplyAsync(()->timeSleep(1)).thenCombine(
                CompletableFuture.supplyAsync(()->timeSleep(2)),(x,y)->x+y)
                .thenAccept(r-> {
                    assertEquals(r.intValue(), 3);
                    assertTrue((System.currentTimeMillis()-s)<1100);
                }).join();
    }

    @Test
    public void testHandleExceptionTasks() {
        // 直接抛出原始异常
        try {
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException();
            }).thenAccept(s-> System.out.println("not enter")).join();
        } catch (RuntimeException e) {
            // ignore
        } catch (Exception e) {
            assertTrue(false);
        }
        // 抛出异常后，进一步处理
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        }).handle((x,t)->{
            if (t == null) {
                return x;
            }
            t.printStackTrace();
            return "error";
        }).thenAccept(s->assertEquals("error", s)).join();
    }

    @Test
    public void testCompositeMultiTasks() {
        long s = System.currentTimeMillis();
        // 将多个异步请求组合在一起，然后将所有的值组合成另外一个对象再返回
        CompletableFuture c = CompletableFuture.supplyAsync(()->{
            CombinedValue v = new CombinedValue();
            v.setA(timeSleep(1));
            return v;
            })
            .thenCombine(CompletableFuture.supplyAsync(()->timeSleep(2)),(x,y)->{
                x.setB(y);
                return x;
            })
            .thenCombine(CompletableFuture.supplyAsync(()->timeSleep(3)),(x,y)->{
                x.setC(y);
                return x;
            })
            .thenApply(CombinedValue::count);
        assertEquals(c.join(), 6);
        assertTrue((System.currentTimeMillis()-s)<1100);
    }

    @Test
    public void testSequencesMultiTasks() {
        long s = System.currentTimeMillis();
        // 将带有请求顺序的调用使用同一个CompletableFuture，最后再把多个聚合组合在一起
        CompletableFuture<CombinedValue> c = CompletableFuture.supplyAsync(()->{
            CombinedValue v = new CombinedValue();
            v.setA(timeSleep(1));
            return v;
            })
            .thenApply(x->{x.setB(timeSleep(2));return x;})
            .thenApply(x->{x.setC(timeSleep(3));return x;});

        CompletableFuture c2 = CompletableFuture.supplyAsync(()->{
            CombinedValue v = new CombinedValue();
            v.setE(timeSleep(4));
            return v;
        })
            .thenApply(x->{x.setF(timeSleep(5));return x;})
            .thenApply(x->{
                CombinedValue v = c.join();
                x.setA(v.getA());
                x.setB(v.getB());
                x.setC(v.getC());
                return x;
            })
            .thenApply(CombinedValue::count);

        assertEquals(c2.join(), 15);
        assertTrue((System.currentTimeMillis()-s)<3100);
    }

    /**
     * 通过循环的方式批量执行Runnable请求
     */
    @Test
    public void testForeachTasks() {
        long s = System.currentTimeMillis();
        List<CompletableFuture<?>> futures = Stream.of(1,2,3,4,5)
                .map(v->CompletableFuture.runAsync(()-> timeSleep(v))).collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        long elapsed = System.currentTimeMillis() -s;
        log.info("一共耗时:{}", elapsed);
        assertTrue(elapsed < 5200);
        futures.forEach(e->assertTrue(e.isDone()));
        timeSleep();
    }

    /**
     * 通过循环的方式批量执行Callable请求
     */
    @Test
    public void testForeachFutures() {
        long s = System.currentTimeMillis();
        List<CompletableFuture<Integer>> futures = Stream.of(1,2,3,4,5)
                .map(v->CompletableFuture.supplyAsync(()->timeSleep(v))).collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        long elapsed = System.currentTimeMillis() - s;
        log.info("一共耗时:{}", elapsed);
        assertTrue(elapsed < 5200);
        futures.forEach(e->
        {
            assertTrue(e.isDone());
            Arrays.asList(1,2,3,4,5).contains(e.getNow(999));
            log.info("return  value:{}",e.getNow(999));
        });
        timeSleep();
    }

    private int timeSleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    private int timeSleep() {
        return timeSleep(1);
    }

    private class CombinedValue{
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public int getC() {
            return c;
        }

        public int getD() {
            return d;
        }

        public int getE() {
            return e;
        }

        public int getF() {
            return f;
        }

        public void setA(int a) {
            this.a = a;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setC(int c) {
            this.c = c;
        }

        public void setD(int d) {
            this.d = d;
        }

        public void setE(int e) {
            this.e = e;
        }

        public void setF(int f) {
            this.f = f;
        }

        public int count() {
            return a+b+c+d+e+f;
        }
    }
}
