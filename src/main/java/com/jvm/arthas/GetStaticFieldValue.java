package com.jvm.arthas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author chendurex
 * @date 2018-11-18 12:02
 */
public class GetStaticFieldValue {

    private static Map<Integer, Integer> boxedMap = ImmutableMap.of(1,1,2,2,3,3);
    private static InnerValue innerValue = new InnerValue();
    private static List<InnerValue> innerValues = Arrays.asList(new InnerValue(), new InnerValue());
    private static Map<EnumValue, Integer> enumValue = ImmutableMap.of(EnumValue.ONE, 111, EnumValue.TWO, 222, EnumValue.THREE, 333);
    private static class InnerValue {
        private static List<Map<Integer, Integer>> staticlist = ImmutableList.of(ImmutableMap.of(3,3,4,4));
        private List<Map<Integer, Integer>> insList = ImmutableList.of(ImmutableMap.of(6,6,7,7));
        private Integer v = 110;
    }

    private static enum EnumValue {
        ONE, TWO, THREE
    }

    /**
     * 执行如下命令可以获取Map集合信息，但是只能获取对象内部基本值，如果内部是对象，那么会打印出对象地址
     * getstatic com.jvm.arthas.GetStaticFieldValue boxedMap
     * 也可以使用Map内部提供的keys values命令获取对应的key和values
     * getstatic com.jvm.arthas.GetStaticFieldValue boxedMap keys
     * getstatic com.jvm.arthas.GetStaticFieldValue boxedMap values
     */
    private void getBoxedMapCommand() {

    }

    /**
     * 可以直接使用 -x 参数，表示打印对象的深度
     * getstatic com.jvm.arthas.GetStaticFieldValue innerValue insList -x 2
     *
     * 迭代内部类静态属性，注意静态内部类不能通过实例类直接获取
     * getstatic com.jvm.arthas.GetStaticFieldValue$InnerValue staticlist 'iterator().next()'
     *
     * 指定迭代对象中某个属性值，后面的{v}表示只显示v这个属性
     * 用法相当于Java stream中的map函数
     * getstatic com.jvm.arthas.GetStaticFieldValue innerValues 'iterator().next().{v}'
     *
     * 根据条件过滤
     * {? #this.v > 100} 相当于stream里面的filter，后面的name相当于stream里面的map
     * getstatic com.jvm.arthas.GetStaticFieldValue innerValues 'iterator().next().{? #this.v>100}.{v}'
     * 如果要找到第一个v大于100的v，怎么办呢？可以用^或$来进行第一个或最后一个的匹配
     * getstatic com.jvm.arthas.GetStaticFieldValue innerValues 'iterator().next().{^ #this.v>100}.{v}'
     * getstatic com.jvm.arthas.GetStaticFieldValue innerValues 'iterator().next().{$ #this.v>100}.{v}'
     */
    private void getInnerValue() {

    }

    /**
     * 根据某个key获取指定的数据，可以使用ognl表达式
     * 根据基本类型获取value
     * getstatic com.jvm.arthas.GetStaticFieldValue boxedMap 'entrySet().iterator.{? #this.key==1}'
     * 根据枚举类型获取指定值
     * getstatic com.jvm.arthas.GetStaticFieldValue enumValue 'entrySet().iterator.{? #this.key.name()=="ONE"}'
     */
    private void getDelicatedValue() {

    }


    public static void main(String[] args) throws Exception {

        for (; ; ) {
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
