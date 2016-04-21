package com.design.patterns.filter_patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.filter_patterns
 * @date 2016/4/1 9:25
 */
public class FemaleCriterial implements Criterial {
    @Override
    public  List<Person>  filterCriterial(List<Person> personList) {
        List<Person> list = new ArrayList<>();
        for (Person person : personList) {
            if (person.getGender().equalsIgnoreCase("female")) {
                list.add(person);
            }
        }
        return list;
    }
}
