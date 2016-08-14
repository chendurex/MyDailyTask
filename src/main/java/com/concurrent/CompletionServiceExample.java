package com.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceExample {

    @Test
    public void execute() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletionService<String> taskCompletionService = new ExecutorCompletionService<>(
                executorService);

        try {
            List<Callable<String>> callables = createCallableList();
            for (Callable<String> callable : callables) {
                taskCompletionService.submit(callable);
            }
            for (int i = 0; i < callables.size(); i++) {
                Future<String> result = taskCompletionService.take();
                System.out.println(result.get());
            }
        } catch (InterruptedException e) {
            // no real error handling. Don't do this in production!
            e.printStackTrace();
        } catch (ExecutionException e) {
            // no real error handling. Don't do this in production!
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public List<Callable<String>> createCallableList() {
        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            callables.add(new LongRunningTask());
        }
        return callables;
    }

    class LongRunningTask implements Callable<String> {

        public String call() {
            // do stuff and return some String
            try {
                Thread.sleep(Math.abs(new Random().nextLong() % 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return Thread.currentThread().getName();
        }
    }
}