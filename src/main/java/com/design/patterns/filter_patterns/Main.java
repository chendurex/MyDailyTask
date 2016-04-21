package com.design.patterns.filter_patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.filter_patterns
 * @date 2016/4/1 9:42
 */
public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("a","female","1");
        Person person2 = new Person("b","male","2");
        Person person3 = new Person("c","female","2");
        Person person4 = new Person("d","male","2");
        Person person5 = new Person("e","female","1");
        List<Person> personList = new ArrayList<>(8);
        personList.add(person1);personList.add(person2);personList.add(person3);personList.add(person4);personList.add(person5);
        Criterial female = new FemaleCriterial();
        Criterial male = new MaleCriterial();
        Criterial and = new AndCriterial(female,male);
        Criterial or = new OrCriterial(female,male);
        for (Person person : and.filterCriterial(personList)) {
            System.out.println(person.toString());
        }
        System.out.println("-----------------");
        for (Person person : or.filterCriterial(personList)) {
            System.out.println(person.toString());
        }
    }
}
