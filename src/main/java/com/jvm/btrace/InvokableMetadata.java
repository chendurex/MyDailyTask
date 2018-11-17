package com.jvm.btrace;

import lombok.Data;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author chendurex
 * @date 2018-11-17 18:05
 */
@Data
public class InvokableMetadata {
    private String name;
    private Integer age;
    private String gender;
    private HashMap<String,String> map = new HashMap<>();

    private void setName(String name) {
        this.name = name;
        map.put(name, name);
    }
    private String getName() {
        return map.get(name);
    }

    private boolean contains(String name) {
        System.out.println(map.get(name));
        return map.containsKey(name);
    }

    private void gc() {
        System.gc();
    }

    private InvokableMetadata getThis() {
        return this;
    }

    private void run(InvokableMetadata invoker) {
        invoker.setName("1");
        String name = invoker.getName();
        System.out.println(name);
        invoker.contains("1");
        invoker.gc();
        InvokableMetadata me = invoker.getThis();
        System.out.println(me);
    }

    public static void main(String[] args) throws Exception {
        for (; ; ) {
            InvokableMetadata invoker = new InvokableMetadata();
            invoker.run(invoker);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
