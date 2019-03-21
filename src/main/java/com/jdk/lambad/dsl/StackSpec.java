package com.jdk.lambad.dsl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static com.jdk.lambad.dsl.Description.*;

/**
 * @author cheny.huang
 * @date 2019-03-19 10:24.
 */
public class StackSpec {
    @Test
    public void testDslLanguage() {
        describe("a stack", it ->{
            it.should("be empty when created", expect -> {
                expect.that(new Stack<>()).isEmpty();
            });
            it.should("push new elements onto the top of the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(1);
                expect.that(stack.get(0)).isEquals(1);
            });
            it.should("pop the last element pushed onto the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(2);
                stack.push(1);
                expect.that(stack.pop()).isEquals(2);
            });
        });
    }
}
