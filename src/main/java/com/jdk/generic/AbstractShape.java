package com.jdk.generic;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

/**
 * @author cheny.huang
 * @date 2019-02-12 14:54.
 * 引自 io.netty.bootstrap.AbstractBootstrap
 */
abstract class AbstractShape<A extends AbstractShape<A, B>, B extends Collection> {
    /**
     * 通过定义泛型的方式让子类获取自身实现而不是父类
     * @return A
     */
    @SuppressWarnings("unchecked")
    A self() {
        return (A)this;
    }

    abstract void say(B b);
}

class Rectangle extends AbstractShape<Rectangle, List<String>>{

    void rec() {

    }

    @Override
    void say(List<String> strings) {

    }
}

class Square extends AbstractShape<Square, Queue<String>> {

    void square() {

    }

    @Override
    void say(Queue<String> strings) {

    }
}

class Boot {
    public static void main(String[] args) {
        // 如果子类有特殊的方法，那么不用强制转换为子类
        AbstractShape<Rectangle, List<String>> rectangle = new Rectangle();
        rectangle.self().rec();


        AbstractShape<Square, Queue<String>> square = new Square();
        square.self().square();
    }
}