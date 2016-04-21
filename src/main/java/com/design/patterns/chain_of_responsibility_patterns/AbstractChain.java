package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/25 9:05
 */
public abstract class AbstractChain {
    private AbstractChain abstractChain = null;
    public void process() {
        this.doProcess();
        if (abstractChain != null) {
            abstractChain.process();
        }
    }

    protected AbstractChain setProcess(AbstractChain abstractChain) {
        this.abstractChain = abstractChain;
        return this;
    }

    protected abstract void doProcess();
}
