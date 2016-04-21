package com.design.patterns.filter_patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.filter_patterns
 * @date 2016/4/1 9:26
 */
public class MaleCriterial implements Criterial {
    @Override
    public  List<Person>  filterCriterial(List<Person> personList) {
        List<Person> list = new ArrayList<>();
        for (Person person : personList) {
            if (person.getMaritalStatus().equalsIgnoreCase("1")) {
                list.add(person);
            }
        }
        return list;
    }
}
