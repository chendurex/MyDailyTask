package com.jdk.lambad;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * @author cheny.huang
 * @date 2019-03-11 17:17.
 */
public class LambdaTutorial {
    @Test
    public void testReduce() {
        assertEquals(6, Stream.of(1,2,3).reduce(0, (x, y)->x+y).intValue());
        GroupDemo []groupDemo = {new GroupDemo(1,"first"),new GroupDemo(1,"second"),new GroupDemo(3,"third")};
        StringBuffer reduced =
                Stream.of(groupDemo)
                        .map(GroupDemo::getB)
                        // .ParallelTutorial() // 如果开启并行操作，那么会无序的并行执行(所以千万不要想当然的流还是按照正常的顺序输出)
                        // reduce中第一个参数代表初始值，如果这个值是带状态的(比如对象),那么整个执行过程都是使用这个参数，如果是基本类型，那么就是属于无状态的
                        // 请重点注意：如果是带状态的，那么第二个参数中传入的参数就一直是同一个对象，这个在串行的过程中没有任何问题
                        // 但是在并行的过程中就需要进行线程安全保护了
                        // 第三个参数是属于并行调用需要使用到的参数，如果是串行的话，是不需要的；它表示的意思是将每个部分的执行
                        // 结果合并成最终的结果，但是但是，如果第一个参数的带状态的，那么就一定需要注意到合并操作的必要性了
                        // 比如下面这个例子，其实在第二个聚合操作中就已经获取到结果了，如果第三个操作继续聚合的话会得到错误的结果
                        .reduce(new StringBuffer(), (builder, name) -> {
                            if (builder.length() > 0) {
                                builder.append(",");
                            }
                            builder.append(name);
                            return builder;
                        }, (s,m)->s);
        reduced.insert(0, "[");
        reduced.append("]");
        assertEquals(reduced.toString(), "[first,second,third]");
        // 无状态操作
        assertEquals(10, Stream.of(1,2,3,4).parallel().reduce(0,(x,y)->x+y,(x,y)->x+y).intValue());
        Map<Integer, List<GroupDemo>> groups = Stream.of(groupDemo).reduce(new HashMap<>(),
                (m,g)->{m.computeIfAbsent(g.getA(), k->new ArrayList<>()).add(g);return m;},
                (m,g)->m);
        assertEquals(groups.get(1).size(), 2);
        assertEquals(groups.get(3).size(), 1);
    }

    @Test
    public void testToLong() {
        int [] toL = Stream.of("1","2").mapToInt(Integer::valueOf).toArray();
        int [] toL2 = Stream.of(1,2).mapToInt(Integer::valueOf).toArray();
        List<String> ls = Stream.of(1,2).mapToInt(Integer::valueOf).mapToObj(String::valueOf).collect(Collectors.toList());
        Assert.assertArrayEquals(toL, toL2);
        assertEquals(Arrays.asList("1","2"), ls);
    }

    @Test
    public void testGroupBy() {
        GroupDemo []groupDemo = {new GroupDemo(1,"1"),new GroupDemo(1,"2"),new GroupDemo(3,"3")};
        // partition
        Map<Boolean, List<GroupDemo>> simpleGroup = Stream.of(groupDemo).collect(Collectors.partitioningBy(m ->m.getA()>1));
        assertEquals(1, simpleGroup.get(Boolean.TRUE).size());
        assertEquals(2, simpleGroup.get(Boolean.FALSE).size());
        // equivalent MultiHashMap or sql group by
        Map<Integer, List<GroupDemo>> groups = Stream.of(groupDemo).collect(Collectors.groupingBy(GroupDemo::getA));
        assertEquals(2, groups.get(1).size());
        assertEquals(1, groups.get(3).size());
        // convert to map while has not duplicate key,other wise throw duplicate key exception
        Map<Integer, GroupDemo> groupDemoMap = Stream.of(groupDemo).collect(Collectors.toMap(GroupDemo::getA, v->v));
        assertEquals(2, groupDemoMap.size());
    }

    @Test
    public void testCombine() {
        GroupDemo []groupDemo = {new GroupDemo(1,"first"),new GroupDemo(1,"second"),new GroupDemo(3,"third")};
        // 把数据分组，然后再统计分组的数据
        Map<Integer, Long> count = Stream.of(groupDemo).collect(Collectors.groupingBy(GroupDemo::getA, Collectors.counting()));
        assertEquals(2, (long)count.get(1));
        assertEquals(1, (long)count.get(3));
        // 聚合一个reduce
        Map<Integer, Integer> count2 = Stream.of(groupDemo).collect(Collectors.groupingBy(
                GroupDemo::getA, Collectors.reducing(0, GroupDemo::getA,Integer::sum)));
        assertEquals(2, (int)count2.get(1));
        assertEquals(3, (int)count2.get(3));
        Map<Integer, Integer> count3 = Stream.of(groupDemo).collect(Collectors.groupingBy(
                GroupDemo::getA, Collectors.summingInt(GroupDemo::getA)));
        assertEquals(2, (int)count3.get(1));
        assertEquals(3, (int)count3.get(3));
        // 把数据分组，然后再把分组后的子数据再次计算，得到分组后的分组数据
        Map<Integer, String> v = Stream.of(groupDemo)
                .collect(Collectors.groupingBy(GroupDemo::getA, Collectors.mapping(GroupDemo::getB, Collectors.joining(","))));
        assertEquals("first,second", v.get(1));
        assertEquals("third", v.get(3));
        // 不使用组合方式实现上述功能
        // 首先将数据进行分组
        Map<Integer, List<GroupDemo>> group = Stream.of(groupDemo).collect(Collectors.groupingBy(GroupDemo::getA));
        // 然后再重新映射流最后再聚合数据得到结果
        Map<Integer, String> v2 = group.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        x->x.getValue().stream()
                                .map(GroupDemo::getB)
                                .collect(Collectors.joining(","))));
        assertEquals("first,second", v2.get(1));
        assertEquals("third", v2.get(3));
        // Collectors.mapping提供了一个组合下游数据的方式，因为所有的聚合都是Collector子类，所以可以无限聚合，
        // 我们来玩一个更加高级的，重复聚合多次获取数据
        // 首先生成一个多层嵌套的对象
        Map<Integer, String> v3 = Stream.of(groupDemo).map(s->new GroupDemo(s.getA(), s.getB(), new Sub(new SubSub(s.getB()))))
                // 将GroupDemo按照字段a进行分组
                .collect(Collectors.groupingBy(GroupDemo::getA,
                        // 将分组后的GroupDemo继续处理内部的sub子对象
                        Collectors.mapping(GroupDemo::getSub,
                                // 继续处理sub子对象中的subSub子对象
                                Collectors.mapping(Sub::getSubSub,
                                        // 最后获取到subSub对象中的name字段，然后将值拼接成字符串返回
                                        Collectors.mapping(SubSub::getName, Collectors.joining(","))))));
        assertEquals("first,second", v3.get(1));
        assertEquals("third", v3.get(3));
        // 下面是使用命令式方式实现
        Map<Integer, String> v4 = new HashMap<>();
        Map<Integer, List<GroupDemo>> groupDemoMap = new HashMap<>();
        for (GroupDemo demo : groupDemo) {
            GroupDemo newer = new GroupDemo(demo.getA(), demo.getB(), new Sub(new SubSub(demo.getB())));
            groupDemoMap.computeIfAbsent(demo.getA(), k->new ArrayList<>()).add(newer);
        }
        for (Map.Entry<Integer, List<GroupDemo>> entry : groupDemoMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (GroupDemo g : entry.getValue()) {
                sb.append(",").append(g.getSub().getSubSub().getName());
            }
            v4.put(entry.getKey(), sb.substring(1));
        }
        assertEquals(v4.get(1), v3.get(1));
        assertEquals(v4.get(3), v3.get(3));
    }

    @Test
    public void testStringConcat() {
        GroupDemo []groupDemo = {new GroupDemo(1,"first"),new GroupDemo(2,"second"),new GroupDemo(3,"third")};
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i=0;i<groupDemo.length;i++) {
            if (i == 0) {
                sb.append(groupDemo[i].getB());
            } else {
                sb.append(",").append(groupDemo[i].getB());
            }
        }
        sb.append("]");
        assertEquals("[first,second,third]", sb.toString());
        // equivalent
        String v = Stream.of(groupDemo).map(GroupDemo::getB).collect(Collectors.joining(",","[","]"));
        assertEquals(v, sb.toString());
    }

    private class GroupDemo {
        private int a;
        private String b;
        private Sub sub;
        GroupDemo(int a, String b) {
            this.a = a;
            this.b = b;
        }

        GroupDemo(int a, String b, Sub sub) {
            this.a = a;
            this.b = b;
            this.sub = sub;
        }

        public int getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public Sub getSub() {
            return sub;
        }
    }
    private class Sub {
        private SubSub subSub;

        Sub(SubSub subSub) {
            this.subSub = subSub;
        }

        public SubSub getSubSub() {
            return subSub;
        }
    }

    private class SubSub {
        private String name;

        SubSub(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
