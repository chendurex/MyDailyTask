package com.jdk.lambad;

import org.junit.Test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author cheny.huang
 * @date 2019-03-18 17:08.
 */
public class ParallelTutorial {
    @Test
    public void testDiceRolls() {
        int n = 100;
        double fraction = 1.0/ n;
        Map<Integer,DoubleSummaryStatistics> v = IntStream.range(0, n)
        .parallel()
        .mapToObj(s->ThreadLocalRandom.current().nextInt(1,7)+ThreadLocalRandom.current().nextInt(1,7))
                .collect(Collectors.groupingBy(side -> side, Collectors.summarizingDouble(nn->fraction)));
        System.out.println(v);
    }

    @Test
    public void testSimpleMovingAverage() {
        double[] sums = {1.0,2.0,3.0,4.0,5.0};
        Arrays.parallelPrefix(sums, Double::sum);
        System.out.println(Arrays.toString(sums));
        int n = 2;
        int start = n - 1;
        double[] average = IntStream.range(start, sums.length)
                .mapToDouble(i -> {
                    double prefix = i == start ? 0 : sums[i - n];
                    return (sums[i] - prefix) / n;
                })
                .toArray();
        System.out.println(Arrays.toString(average));
    }

    @Test
    public void testPeek() {
        List<String> v = Stream.of(1,2,3,4,5,6)
                .peek(System.out::println)
                .map(String::valueOf)
                .peek(s->{
                    System.out.println();
                    System.out.println(s);
                }).collect(Collectors.toList());
        System.out.println(v);
    }
}
