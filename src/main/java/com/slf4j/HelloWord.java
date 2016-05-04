package com.slf4j;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.slf4j
 * @date 2016/4/1 10:36
 */
public class HelloWord {
    private static Logger logger = LoggerFactory.getLogger(HelloWord.class);
    private static Marker fatal = MarkerFactory.getMarker("FATAL");
    public static void main(String[] args) {
        logger.info("hello word");
        String name  = "haha";
        String gender = "male";
        List<String> list = new ArrayList<>();
        list.add("1");list.add("2");
        logger.info("my name is : {},and my gender is : {}",name,gender);
        logger.info("list==={}"+list,list);

        try {
            Integer.parseInt("ff");
        }catch (Exception e) {
            logger.error( "Failed to obtain JDBC connection", e);
        }
        // 设置某个类的level
        ch.qos.logback.classic.Logger logger1 = (ch.qos.logback.classic.Logger)logger;
        logger1.setLevel(Level.INFO);
        logger1.info("test");
    }
}
