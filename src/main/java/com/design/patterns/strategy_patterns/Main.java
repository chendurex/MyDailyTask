package com.design.patterns.strategy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.strategy_patterns
 * @date 2016/4/12 9:12
 */
public class Main {
    public static void main(String[] args) {
        ContextStrategy strategy1 = new ContextStrategy(new AddStrategy());
        int number1 = strategy1.executeAction(1,3);
        System.out.println(number1);
        ContextStrategy strategy2 = new ContextStrategy(new ReduceStrategy());
        int number2 = strategy2.executeAction(1,3);
        System.out.println(number2);
        ContextStrategy strategy3 = new ContextStrategy(new MultipyStrategy());
        int number3 = strategy3.executeAction(1,3);
        System.out.println(number3);
    }
}
