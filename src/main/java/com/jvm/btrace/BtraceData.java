package com.jvm.btrace;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/2.
 */
@Data
public class BtraceData {
    private String name;
    private Integer age;
    private String gender;
    private HashMap<String,String> map = new HashMap<>();

    public void setName(String name) {
        this.name = name;
        map.put(name, name);
    }
    public String getName() {
        return map.get(name);
    }
}
