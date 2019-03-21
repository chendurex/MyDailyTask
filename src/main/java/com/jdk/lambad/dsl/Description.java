package com.jdk.lambad.dsl;


/**
 * @author cheny.huang
 * @date 2019-03-19 10:16.
 */
public class Description {
    public static void describe(String name, Suite behavior) {
        Description description = new Description();
        behavior.specifySuite(description);
    }

    public void should(String description, Specification specification) {
        Expect expect = new Expect();
        specification.specifyBehaviour(expect);

    }
}
