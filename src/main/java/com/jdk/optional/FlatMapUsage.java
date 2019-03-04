package com.jdk.optional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * @author cheny.huang
 * @date 2019-03-04 10:10.
 */
public class FlatMapUsage {
    @Test
    public void convertStringToUpperCaseStreams() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertEquals(asList("A", "B", "HELLO"), collected);
    }

    @Test
    public void testFlatMap() throws Exception {
        /**
         *           |--->stream one--->output
         * stream ---
         *          |--->stream two--->output
         */
        // 定义一个流，内部包含两个list集合，然后再将内部集合转换成一个集合，这个时候就变成了两个流了，最后分开处理得到两个单独的流
        List<Stream> together2 = Stream.of(asList(1,2), asList(3,4))
                .map(List::stream)
                .map(s->s.map(i ->i++))
                .collect(Collectors.toList());
        assertEquals(together2.size(), 2);
        /**
         *           |--->stream one---|
         * stream ---                  ->merged to another stream--->output
         *          |--->stream two---|
         */
        // 定义一个流，内部包含一个list集合，然后再将list集合转换成一个另外一个流，最后通过flatMap把所有的流合并成一个流
        // 而普通的Map仅仅是对流进行隔离处理，并不会对流进行合并，也就是为什么叫做flattened，它是把流压平了
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(List::stream)
                .map(integer -> integer + 1)
                .collect(Collectors.toList());
        assertEquals(asList(2, 3, 4, 5), together);
        //Stream.of(asList(1,2), asList(3,4)).map(List::stream).map(i -> i++)
        // 如果使用上面的方式，其实Map返回的是一个list集合的流，所以无法执行i++操作
    }

    @Test
    public void testStringFlatMap() {
        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
		List<String> stream = Arrays.stream(data)
                .flatMap(Arrays::stream)
                .filter("a"::equals)
                .collect(Collectors.toList());
    }

    @Test
    public void testGetStudent() {
        Student obj1 = new Student();
        obj1.setName("mkyong");
        obj1.addBook("Java 8 in Action");
        obj1.addBook("Spring Boot in Action");
        obj1.addBook("Effective Java (2nd Edition)");

        Student obj2 = new Student();
        obj2.setName("zilap");
        obj2.addBook("Learning Python, 5th Edition");
        obj2.addBook("Effective Java (2nd Edition)");

        List<Student> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);

        List<String> collect =
                list.stream()
                        .map(Student::getBook)
                        .flatMap(Collection::stream)
                        .distinct()
                        .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @Test
    public void primitiveFlatMap() {
        int[] intArray = {1, 2, 3, 4, 5, 6};

        //1. Stream<int[]>
        Stream<int[]> streamArray = Stream.of(intArray);

        //2. Stream<int[]> -> flatMap -> IntStream
        IntStream intStream = streamArray.flatMapToInt(Arrays::stream);

        intStream.forEach(System.out::println);
    }
}
