package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/25 9:16
 */
public class Main {
    public static void main(String[] args) {
        AbstractChain chainOne = new ChainOne();
        AbstractChain chainTwo = new ChainTwo();
        AbstractChain chainThree = new ChainThree();
        chainOne.setProcess(chainTwo);
        chainTwo.setProcess(chainThree);
        chainOne.process();
    }
}
