package com.jvm.arthas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author chendurex
 * @date 2018-11-17 14:05
 */
public class ParamsAndReturn {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Metadata {
        private int intV;
        private boolean boolV;
        private char charV;
        private double doubV;
        private Integer intBoxedV;
        private Boolean boolBoxedV;
        private Character charBoxedV;
        private Double doubBoxedV;
        private String sV;
        private Map<Integer, Integer> mapV = new HashMap<>();
        private List<Integer> listV = new ArrayList<>();
        private List<Map<Integer, Integer>> lmv = new ArrayList<>();
        private Map<Integer, List<Integer>> mlv = new HashMap<>();
        private Metadata.Stub stub;
        private List<Metadata.Stub> lstub = new ArrayList<>();
        private static int staticV = 11111111;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        static class Stub {
            private Integer intV;
            private Metadata.SubStub subStub;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        static class  SubStub {
            private Integer intV;
            private Map<Integer, Integer> map = new HashMap<>();
            private List<Integer> list = new ArrayList<>();
        }

    }

    /**
     * 打印入参对象值，这里会把所有的参数都打印，如果发现并未打印完全，可以通过 -x参数配合，x表示遍历深度
     * watch com.jvm.arthas.ParamsAndReturn metadata params
     * @param metadata
     */
    private Metadata metadata(Metadata metadata) {
        return metadata;
    }

    /**
     * 打印所有的入参
     * watch com.jvm.arthas.ParamsAndReturn primitive params
     * 打印所有入参和返回值，因为带有返回值，所以需要只用 -x参数，打印二级参数值
     * watch com.jvm.arthas.ParamsAndReturn primitive {params,returnObj} -x 2
     */
    private boolean primitive(byte j, short s, int i, long l, double d, float f, boolean b, char c) {
        return b;
    }

    /**
     * 如果是基本类型或者包装类型的参数，可以直接打印值
     * @see #primitive(byte, short, int, long, double, float, boolean, char)
     */
    private Boolean boxedPrimitive(Byte j, Short s, Integer i, Long l, Double d, Float f, Boolean b, Character c) {
        return b;
    }

    /**
     * 打印集合内容，因为集合本身不是原始数据，所以需要打印二级对象
     * watch com.jvm.arthas.ParamsAndReturn collection params -x 2
     * 打印集合内容和返回值，因为{params,returnObj}算一个对象，params中list和map算二级对象，所以list和map中的对象变为三级对象
     * watch com.jvm.arthas.ParamsAndReturn collection {params,returnObj} -x 3
     */
    private List<Integer> collection(List<Integer> l, Map<Integer, Integer> m) {
        return l;
    }

    /**
     * 进入方法前和方法结束后打印参数内容
     * 如果参数是对象那么是存在修改参数内容的
     * watch com.jvm.arthas.ParamsAndReturn map {params,returnObj} -x 3 -b -f
     *
     * watch 命令定义了4个观察事件点，即 -b 方法调用前，-e 方法异常后，-s 方法返回后和 -f 方法结束后
     * 4个观察事件点 -b、-e、-s 默认关闭，-f 默认打开，当指定观察点被打开后，在相应事件点会对观察表达式进行求值并输出
     * 这里要注意方法入参和方法出参的区别，有可能在中间被修改导致前后不一致，除了 -b 事件点 params 代表方法入参外，其余事件都代表方法出参
     * 当使用 -b 时，由于观察事件点是在方法调用前，此时返回值或异常均不存在
     */
    private Map<Integer, Integer> map(List<Integer> l, Map<Integer, Integer> m) {
        m.put(666,666);
        m.remove(1);
        return m;
    }

    /**
     * 监控参数内容为666的请求
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params[0].intV==666' -x 2 -b
     * 如果方法有重载，则监控参数长度为1的请求
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1' -x 2 -b
     * 监控参数长度为1，请求内容为666的请求
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1 && params[0].intV==666'  -x 2 -b -f
     * 监控参数长度为1，返回内容不为空而且intV的值是111的请求
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1 && returnObj.intV==111'  -x 2 -f
     * 监控参数长度为1，返回内容不为空而且intV的值是111的请求(由于增加了入参和出参，所以会监控两条数据，但是入参的那条数据返回值为null，所以需要判断下)
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1 && returnObj!=null && returnObj.intV==111'  -x 2 -f
     * 监控请求参数为666或者返回内容为111的请求(会返回两条数据，第一条数据是入参的内容，第二条是出参的内容)
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1 && (params[0].intV==666 or (returnObj!=null && returnObj.intV==111 )) '  -x 2 -b -f
     * 监控请求参数中map包含key为1的，并且返回值intV=111的请求(因为条件严格限制了，所以只能打印返回内容，无法打印入参了)
     * watch com.jvm.arthas.ParamsAndReturn subStub '{params,returnObj}' 'params.length==1 && (params[0].map.containsKey(1) && (returnObj!=null && returnObj.intV==111 )) '  -x 2 -f
     * @param subStub
     * @return
     */
    private Metadata.SubStub subStub(Metadata.SubStub subStub) {
        subStub.setIntV(111);
        subStub.setList(Arrays.asList(1,2,3));
        return subStub;
    }

    /**
     * 监控抛出异常
     * watch com.jvm.arthas.ParamsAndReturn throwExp "{params, throwExp}"  -e -x 2
     */
    private void throwExp() {
        throw new RuntimeException();
    }

    private List<Integer> insList = ImmutableList.of(1234,5678);

    /**
     * 观察当前全局对象属性，target当前执行方法所在的对象
     * watch com.jvm.arthas.ParamsAndReturn getInstanceField 'target.insList'
     */
    private void getInstanceField() {

    }

    /**
     * 开启正则匹配模式
     * watch -E 'com.jvm.arthas.Params.*' 'map|coll.*|meta[a-z]ata|^pr.*| returnObj
     */
    private void expPattern() {

    }

    public static void main(String[] args) throws Exception {
        ParamsAndReturn invoker = new ParamsAndReturn();
        for (; ; ) {
            // print primitive value
            invoker.primitive((byte)1, (short)2, 3, 4, 5.00, 6.0f, true, '8');

            // print boxed primitive value
            invoker.boxedPrimitive((byte)1, (short)2, 3, 4L, 5.00, 6.0f, true, '8');

            // print collection value
            invoker.collection(Arrays.asList(1,2,3,4), ImmutableMap.of(1,1, 2, 2, 3,3));

            invoker.map(Arrays.asList(1,2,3,4), new HashMap<>(ImmutableMap.of(1,1, 2, 2, 3,3)));
            invoker.getInstanceField();
            Metadata.SubStub subStub = new Metadata.SubStub();
            subStub.setList(Arrays.asList(4, 4, 4));
            subStub.setIntV(666);
            subStub.setMap(ImmutableMap.of(1,1, 2, 2, 3,3));
            invoker.subStub(subStub);
            try {
                invoker.throwExp();
            } catch (Exception e) {
                // ignore
            }


            // print complicated value
            Metadata metadata = Metadata.builder()
                    .intV(1).boolV(true).charV((char)2).doubV(3.00).sV("4").intBoxedV(5).boolBoxedV(true).charBoxedV((char)6).doubBoxedV(7.00)
                    .listV(Arrays.asList(1, 2, 3))
                    .mapV(ImmutableMap.of(88,88,99,99))
                    .lmv(Collections.singletonList(ImmutableMap.of(3, 3, 4, 4)))
                    .mlv(ImmutableMap.of(4, Collections.singletonList(4)))
                    .stub(Metadata.Stub.builder().intV(1)
                            .subStub(Metadata.SubStub.builder().intV(11)
                                    .list(Arrays.asList(22,33)).map(ImmutableMap.of(33, 33, 44, 44)).build()).build())
                    .lstub(Collections.singletonList(Metadata.Stub.builder().intV(99).build()))
                    .build();
            invoker.metadata(metadata);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
