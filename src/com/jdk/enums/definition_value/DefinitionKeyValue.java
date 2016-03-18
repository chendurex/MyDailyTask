package com.jdk.enums.definition_value;

/**
 * Created by LENOVO on 2016/3/16.
 */
public enum  DefinitionKeyValue {
    RED("red",0),BLUE("blue",1),GREEN("green",2);
    private String key;
    private int value;
    private DefinitionKeyValue(String key,int value) {
        this.key= key;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String getKey() {
        return key;
    }
}
