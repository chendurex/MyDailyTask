package com.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author cheny.huang
 * @date 2018-11-27 09:57.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 4, time = 20)
@Threads(100)
@Fork(1)
public class CalendarBenchmark {

    @Benchmark
    public void multiThreadSetAndGetQps() {
        Calendar.getInstance();
    }
}
