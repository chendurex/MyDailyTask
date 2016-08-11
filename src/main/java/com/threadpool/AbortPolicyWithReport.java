package com.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chen
 * @description
 * @pachage com.threadpool
 * @date 2016/8/11 19:16
 */
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    protected static final Logger logger = LoggerFactory.getLogger(AbortPolicyWithReport.class);
    private final String threadName;
    private final String protocol = "http";
    private final String ip = "localhost";
    private final String port = "8080";

    public AbortPolicyWithReport(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        String msg = String.format("Thread pool is EXHAUSTED! Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d), Executor status:(isShutdown:%s, isTerminated:%s," +
                " isTerminating:%s), in %s://%s:%d!", new Object[]{this.threadName, Integer.valueOf(e.getPoolSize()), Integer.valueOf(e.getActiveCount()), Integer.valueOf(e.getCorePoolSize()),
                Integer.valueOf(e.getMaximumPoolSize()), Integer.valueOf(e.getLargestPoolSize()), Long.valueOf(e.getTaskCount()), Long.valueOf(e.getCompletedTaskCount()), Boolean.valueOf(e.isShutdown()),
                Boolean.valueOf(e.isTerminated()), Boolean.valueOf(e.isTerminating()), protocol, ip, Integer.valueOf(port)});
        logger.warn(msg);
        throw new RejectedExecutionException(msg);
    }
}

