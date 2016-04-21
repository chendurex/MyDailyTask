package com.design.patterns.iterator_patterns;


/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.iterator_patterns
 * @date 2016/4/5 9:52
 */
public class Main {
    public static void main(String[] args) {
        NamesIterator<String> namesIterator = new NamesIterator<>();
        namesIterator.put("1");
        namesIterator.put("2");
        namesIterator.put("3");
        namesIterator.put("4");
        for (Iterator iterator = namesIterator.iterator();iterator.hasNext();) {
            System.out.println(iterator.next());
            iterator.remove();
        }
        System.out.println("------------");
        for (Iterator iterator = namesIterator.iterator();iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
}
