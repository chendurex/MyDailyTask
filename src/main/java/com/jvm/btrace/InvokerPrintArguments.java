package com.jvm.btrace;

import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author chendurex
 * @date 2018-11-17 14:05
 */
public class InvokerPrintArguments {
    private void printableMetadata(PrintableMetadata printableMetadata) {
        System.out.println("---------calPrintableMetadata---------");
    }

    private void printPrimitive(byte j, short s, int i, long l, double d, float f, boolean b, char c) {
        System.out.println("---------callPrintPrimitive---------");
    }

    private void printBoxedPrimitive(Byte j, Short s, Integer i, Long l, Double d, Float f, Boolean b, Character c) {
        System.out.println("---------callPintBoxedPrimitive---------");
    }

    private void printCollection(List<Integer> l, Map<Integer, Integer> m) {
        System.out.println("---------callPrintCollection---------");
    }

    public static void main(String[] args) throws Exception {
        InvokerPrintArguments invoker = new InvokerPrintArguments();
        for (; ; ) {
            // print primitive value
            invoker.printPrimitive((byte)1, (short)2, 3, 4, 5.00, 6.0f, true, '8');

            // print boxed primitive value
            invoker.printBoxedPrimitive((byte)1, (short)2, 3, 4L, 5.00, 6.0f, true, '8');

            // print collection value
            invoker.printCollection(Arrays.asList(1,2,3,4), ImmutableMap.of(1,1, 2, 2, 3,3));

            Map<Integer, Integer> i = new HashMap<>();
            i.put(1, 1);
            // print complicated value
            PrintableMetadata printableMetadata = PrintableMetadata.builder()
                    .intV(1).boolV(true).charV((char)2).doubV(3.00).sV("4").intBoxedV(5).boolBoxedV(true).charBoxedV((char)6).doubBoxedV(7.00)
                    .listV(Arrays.asList(1, 2, 3))
                    .mapV(i)
                    .lmv(Collections.singletonList(ImmutableMap.of(3, 3, 4, 4)))
                    .mlv(ImmutableMap.of(4, Collections.singletonList(4)))
                    .stub(PrintableMetadata.Stub.builder().intV(1)
                            .subStub(PrintableMetadata.SubStub.builder().intV(11)
                                    .list(Arrays.asList(22,33)).map(ImmutableMap.of(33, 33, 44, 44)).build()).build())
                    .lstub(Collections.singletonList(PrintableMetadata.Stub.builder().intV(99).build()))
                    .build();
            invoker.printableMetadata(printableMetadata);
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
