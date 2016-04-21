package com.design.patterns.filter_patterns;

import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.filter_patterns
 * @date 2016/4/1 9:24
 */
public interface Criterial {
    List<Person> filterCriterial(List<Person> personList);
}
