package com.jdk.lambad;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

/**
 * @author cheny.huang
 * @date 2019-03-13 21:12.
 */
public class StringCollector {
    @Test
    public void testFundamentalStream() {
        List<String> v =
            Stream.of(1,2,3,4)
            .filter(s->s>1)
            .map(String::valueOf)
            .collect(toList());
        System.out.println(v);
    }

    @Test
    public void testComplexityTask() {
        // 假设有四支股票基金，每支基金有三个人买了，key为购买者唯一id，
        // value为盈利状况，现在我要统计每支基金盈利的用户
        List<Map<Integer, Double>> maps = Arrays.asList(
                ImmutableMap.of(1,0.1,5,2.0,9,-3.0),
                ImmutableMap.of(2,0.1,6,2.0,10,-3.0),
                ImmutableMap.of(3,0.1,7,2.0,11,-3.0),
                ImmutableMap.of(4,-0.1,8,-2.0,12,-3.0));
        List<Map<Integer, Double>> newer = new LinkedList<>();
        for (Map<Integer, Double> map : maps) {
            Map<Integer, Double> earn = new HashMap<>(16);
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                if (entry.getValue() > 0) {
                    earn.put(entry.getKey(), entry.getValue());
                }
            }
            if (!earn.isEmpty()) {
                newer.add(earn);
            }
        }
        System.out.println(Arrays.toString(newer.toArray()));

        newer = maps.stream().map(
                s->s.entrySet().stream()
                    .filter(v->v.getValue()>0)
                    .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .filter(s->!s.isEmpty()).collect(toList());
        System.out.println(Arrays.toString(newer.toArray()));
    }

    private static final Set<String> NON_WORDS = ImmutableSet.<String>
            builder().add("the").add("and").add("of").add("to")
            .add("a").add("it").add("in").add("or").add("is")
            .add("as").add("so").add("but").add("be").build();
    private Map<String,Integer> wordFrequent(String words) {
        TreeMap<String, Integer> wordMap = new TreeMap<>();
        Matcher m = Pattern.compile("\\w+").matcher(words);
        while (m.find()) {
            String word = m.group().toLowerCase();
            if (!NON_WORDS.contains(word)) {
                if (wordMap.get(word) == null) {
                    wordMap.put(word, 1);
                } else {
                    wordMap.put(word, wordMap.get(word)+1);
                }
            }
        }
        return wordMap;
    }

    private List<String> regexToList(String words, String regex) {
        List<String> wordList = new LinkedList<>();
        Matcher m = Pattern.compile(regex).matcher(words);
        while (m.find()) {
            wordList.add(m.group());
        }
        return wordList;
    }

    private Map<String, Integer> wordFrequentOfJava8(String words) {
        final TreeMap<String, Integer> wordMap = new TreeMap<>();
        regexToList(words, "\\w+").stream()
                .map(String::toLowerCase)
                .filter(w -> !NON_WORDS.contains(w))
                .forEach(w->wordMap.merge(w, 1, (a,b)->a+b));
        return wordMap;
    }

    @Test
    public void testWordFrequent() {
        String word = "i'm writing java code, what't code do i execute?";
        Map<String, Integer> v1 = wordFrequent(word);
        Map<String, Integer> v2 = wordFrequentOfJava8(word);
        System.out.println(v2);
        v1.forEach((k,v)-> Assert.assertEquals(v, v2.get(k)));
        v2.forEach((k,v)-> Assert.assertEquals(v, v1.get(k)));
    }
}
