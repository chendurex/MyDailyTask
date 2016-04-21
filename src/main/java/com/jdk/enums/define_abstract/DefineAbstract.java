package com.jdk.enums.define_abstract;

/**
 * Created by LENOVO on 2016/3/17.
 */
public enum DefineAbstract {
    RED(0){
        @Override
        public int getValue() {
            return 99;
        }
    },GREEN(1){
        @Override
        public int getValue() {
            return 98;
        }
    },BLUE(2){
        @Override
        public int getValue() {
            return 97;
        }
    };
    private int value;
    DefineAbstract (int value){
        this.value = value;
    }
    public abstract int getValue();

    public static void main(String[] args) {
        System.out.println(DefineAbstract.RED.getValue());
    }
}
