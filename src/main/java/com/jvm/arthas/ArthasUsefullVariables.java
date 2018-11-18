package com.jvm.arthas;

/**
 * https://alibaba.github.io/arthas/advice-class.html
 * @author chendurex
 * @date 2018-11-18 16:17
 */
public class ArthasUsefullVariables {
    /**
     *     private final ClassLoader loader; 本次调用类所在的 ClassLoader
     *     private final Class<?> clazz; 本次调用类的 Class 引用
     *     private final ArthasMethod method; 本次调用方法反射引用
     *     private final Object target; 本次调用类的实例
     *     private final Object[] params; 本次调用参数列表，这是一个数组，如果方法是无参方法则为空数组
     *     private final Object returnObj; 本次调用返回的对象。当且仅当 isReturn==true 成立时候有效，表明方法调用是以正常返回的方式结束。如果当前方法无返回值 void，则值为 null
     *     private final Throwable throwExp; 本次调用抛出的异常。当且仅当 isThrow==true 成立时有效，表明方法调用是以抛出异常的方式结束。
     *     private final boolean isBefore; 辅助判断标记，当前的通知节点有可能是在方法一开始就通知，此时 isBefore==true 成立，同时 isThrow==false 和 isReturn==false，因为在方法刚开始时，还无法确定方法调用将会如何结束。
     *     private final boolean isThrow; 辅助判断标记，当前的方法调用以抛异常的形式结束
     *     private final boolean isReturn; 辅助判断标记，当前的方法调用以正常返回的形式结束
     */
    private void variables() {

    }

    /**
     * ognl 提供的虚拟集合操作
     * Collection:
     *   size
     *   isEmpty
     * List:
     *   iterator
     * Map:
     *   keys
     *   values
     * Set:
     *   iterator
     *   Iterator:
     * next
     *   hasNext
     *   Enumeration:
     *   next
     *   hasNext
     *   nextElement
     *   hasMoreElements
     */
    private void collections() {

    }
}
