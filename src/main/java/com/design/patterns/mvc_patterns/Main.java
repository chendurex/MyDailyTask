package com.design.patterns.mvc_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.mvc_patterns
 * @date 2016/4/14 9:11
 */
public class Main {
    public static void main(String[] args) {
        StudentController controller = new StudentController(new ViewDetail(),new StudentModel("killy","man"));
        controller.show();
        controller.setStudentName("silly");
        controller.show();
    }
}
