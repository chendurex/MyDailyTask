package com.jdk.enums.definition_value;

/**
 * Created by LENOVO on 2016/3/16.
 * for this to work you need to define a member variable and a constructor because PENNY (1) is actually calling a constructor which accepts int value
 *The constructor of enum in java must be private any other access modifier will result in compilation error.
 *  Now to get the value associated with each coin you can define a public getValue() method inside Java enum
 *  like any normal Java class. Also, the semicolon in the first line is optional
 *
 Read more: http://javarevisited.blogspot.sg/2011/08/enum-in-java-example-tutorial.html
 */
public enum DefinitionValue {
    RED(0),BLUE(1),GREEN(3);
    private int value;
    private DefinitionValue(int value){
        this.value = value;
    }

    //all enum constant is a singleton instance
    //equals instance to call method
    public int getValue() {
        return value;
    }
}
