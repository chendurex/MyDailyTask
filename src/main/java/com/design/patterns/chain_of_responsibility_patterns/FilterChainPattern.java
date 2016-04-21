package com.design.patterns.chain_of_responsibility_patterns;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:02
 */
public class FilterChainPattern {
    private int index = 0;
    private List<FilterPattern> filterPatternList = new LinkedList<>();
    public FilterChainPattern addFilter(FilterPattern filterPattern) {
        filterPatternList.add(filterPattern);
        return this;
    }
    public void doFilter(int number) {
        if (filterPatternList.size() > index) {
            filterPatternList.get(index++).doFilter(number,this);
        } else {
            System.out.println("filter is stop");
        }
    }
}
