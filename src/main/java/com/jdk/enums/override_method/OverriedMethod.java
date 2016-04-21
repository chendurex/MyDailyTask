package com.jdk.enums.override_method;

/**
 * Created by LENOVO on 2016/3/17.
 */
public enum OverriedMethod {
    BLUE,GREEN,RED,YELLOW;

    @Override
    public String toString() {
        String msg = "";
        switch (this) {
            case BLUE:msg = "blue color";break;
            case GREEN:msg = "green color";break;
            case RED:msg = "red color";break;
            case YELLOW:msg = "yellow color";break;
        }
        return msg;
    }

    public static void main(String[] args) {
        System.out.println(OverriedMethod.BLUE);
    }
}
