package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:27
 */
public class FilterMain {
    public static void main(String[] args) {
        FilterPattern filterPatternOne = new FilterPatternImplOne();
        FilterPattern filterPatternTwo = new FilterPatternImplTwo();
        FilterPattern filterPatternThree = new FilterPatternImplThree();
        FilterChainPattern filterChainPattern = new FilterChainPattern();
        filterChainPattern.addFilter(filterPatternOne);
        filterChainPattern.addFilter(filterPatternTwo);
        filterChainPattern.addFilter(filterPatternThree);
        filterChainPattern.doFilter(500);
    }
}
