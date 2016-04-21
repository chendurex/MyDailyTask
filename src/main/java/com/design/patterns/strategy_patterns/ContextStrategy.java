package com.design.patterns.strategy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.strategy_patterns
 * @date 2016/4/12 9:10
 */
public class ContextStrategy {
    private IStrategy strategy;
    public ContextStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }
    public int executeAction(int numb1,int numb2) {
        return strategy.doAction(numb1,numb2);
    }
}
