package com.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author cheny.huang
 * @date 2018-09-11 19:22.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DateBenchMark {
    @Benchmark
    public Calendar runCalendar() {
        return Calendar.getInstance();
    }

    @Benchmark
    public Instant runJoda() {
        return Instant.now();
    }

    @Benchmark
    public long runSystem() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(DateBenchMark.class.getSimpleName())
                .forks(1)
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(1))
                .mode(Mode.AverageTime)
                .build();
        new Runner(opt).run();
    }
}
