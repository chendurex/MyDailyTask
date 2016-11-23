package com.jvm.bytecode;

/**
 * @author chen
 * @date 2016/11/19 16:36
 */
public class FinalCode {

    public static void main(String[] args) {

    }

    private final String name = "1";

    private final String gender;

    private final String age;

    private static final String level;

    private static final String addr = "4";

    public FinalCode() {
        this.gender = "gender";
    }

    public FinalCode(String gender) {
        this.gender = gender;
    }

    public final void run(String value) {
        System.out.println(value);
    }

    public void call(final String value) {
        final FinalCode fn = new FinalCode();
        new Thread() {
            @Override
            public void run() {
                fn.run(value);
            }
        }.start();
    }

    {
        age = "1";
    }

    static {
        level = "3";
    }
}
