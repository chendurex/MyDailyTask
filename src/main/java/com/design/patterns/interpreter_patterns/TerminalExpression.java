package com.design.patterns.interpreter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.interpreter_patterns
 * @date 2016/4/21 9:19
 */
public class TerminalExpression implements Expression {
    private String data;
    public TerminalExpression (String data) {
        this.data = data;
    }
    @Override
    public Boolean interpret(String context) {
        if (data.contains(context)) {
            return true;
        }
        return false;
    }
}
