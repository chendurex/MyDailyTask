package com.jdk.enums.use_switch;

import com.jdk.enums.use_constant.EnumConstant;

/**
 * Created by LENOVO on 2016/3/16.
 */
public class DefinitionSwitch {
    public void swtichMethod(EnumConstant constant) {
        switch (constant) {
            case blue:
                System.out.println("blue");
                break;
            case red:
                System.out.println("red");
                break;
            case green:
                System.out.println("green");
                break;
        }
    }
}
