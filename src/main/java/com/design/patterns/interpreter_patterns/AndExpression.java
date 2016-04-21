package com.design.patterns.interpreter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.interpreter_patterns
 * @date 2016/4/21 9:21
 */
public class AndExpression implements Expression {
    private Expression expression1;
    private Expression expression2;

    public AndExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
    @Override
    public Boolean interpret(String context) {
        return expression1.interpret(context) && expression2.interpret(context);
    }
}
