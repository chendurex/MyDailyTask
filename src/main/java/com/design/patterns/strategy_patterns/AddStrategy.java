package com.design.patterns.strategy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.strategy_patterns
 * @date 2016/4/12 9:08
 */
public class AddStrategy implements IStrategy {
    @Override
    public  int doAction(int numb1,int numb2){
        return numb1 + numb2;
    }
}
