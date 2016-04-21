package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:12
 */
public class FilterPatternImplThree implements FilterPattern {
    @Override
    public void doFilter(int number ,FilterChainPattern filterChainPattern) {
        if (number < 1000) {
            System.out.println("number is less than thousand ");
        } else {
            System.out.println("number is more than thousand ,should process next");
            filterChainPattern.doFilter(number);
        }

    }
}
