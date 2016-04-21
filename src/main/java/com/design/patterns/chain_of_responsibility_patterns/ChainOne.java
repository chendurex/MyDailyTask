package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/25 9:15
 */
public class ChainOne extends AbstractChain {
    @Override
    protected void doProcess() {
        System.out.println("this is chain one");
    }
}
