package com.jvm.btrace;

import static com.sun.btrace.BTraceUtils.printArray;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.strcat;

import java.lang.ref.WeakReference;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils.Aggregations;
import com.sun.btrace.BTraceUtils.Strings;
import com.sun.btrace.aggregation.Aggregation;
import com.sun.btrace.aggregation.AggregationFunction;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Export;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Property;
import com.sun.btrace.annotations.TargetMethodOrField;

// @BTrace means that this class will be used to define probes
// and actions. All fields and methods must be static.
// You can use only BTraceUtils methods.
@BTrace
@SuppressWarnings("unused")
public class CacheMonitorBTrace {

    // This field will be exported as a JStat counter.
    @Export
    private static long dataAccessed;

    // This field will be exported to the MBean server.
    @Property
    private static long dataCreated;

    @Property
    private static long cacheChecked;

    private static Aggregation methodDuration = Aggregations
            .newAggregation(AggregationFunction.AVERAGE);

    /**
     * We want to measure how long does the
     * DataAcccesor#getData(-) method execution take.
     */
    @OnMethod(clazz = "com.jvm.btrace.DataAccessor",
            method = "getData",
            location = @Location(Kind.RETURN))
    public static void addMethodDuration(@Duration long duration) {
        Aggregations.addToAggregation(methodDuration,
                duration / 1000);

        dataAccessed++;
    }

    /**
     * Invoked whenever WeakReference#get(-) is invoked from the
     * DataAccessor#getData(-) method.
     */
    @OnMethod(clazz = "com.jvm.btrace.DataAccessor",
            method = "getData",
            location = @Location(value = Kind.CALL,
                    clazz = "java.lang.ref.WeakReference",
                    method = "get"))
    public static void cacheChecked(
            @TargetMethodOrField(fqn = true) String method) {
        println(strcat("Cache check invoked; method: ", method));

        cacheChecked++;
    }

    /**
     * Invoked when any method from DataAccessor class is entered.
     */
    @OnMethod(clazz = "com.jvm.btrace.DataAccessor",
            method = "/.*/",
            location = @Location(value = Kind.ENTRY))
    public static void dataAccessorMethodEntry(
            @ProbeClassName String className,
            @ProbeMethodName String probeMethod,
            AnyType[] args) {
        println(Strings.strcat("Entered method: ", probeMethod));
        printArray(args);
    }

    /**
     * Invoked whenever new Data object is created from
     * DataAccessor#getData(-) method.
     */
    @OnMethod(clazz = "com.jvm.btrace.DataAccessor",
            method = "getData",
            location = @Location(value = Kind.NEW,
                    clazz = "com.piotrnowicki.btrace.Data"))
    public static void dataCreated() {
        dataCreated++;
    }

    /**
     * Invoked every 10 seconds - not a real probe.
     */
    @OnTimer(value = 10000)
    public static void printAvgMethodDuration() {
        Aggregations.printAggregation(
                "Average method duration (ms)", methodDuration);
    }
}
