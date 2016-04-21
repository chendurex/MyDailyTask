package com.design.patterns.mvc_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.mvc_patterns
 * @date 2016/4/14 9:08
 */
public class StudentController {
    private ViewDetail viewDetail;
    private StudentModel studentModel;
    public StudentController(ViewDetail viewDetail,StudentModel studentModel) {
        this.viewDetail = viewDetail;
        this.studentModel = studentModel;
    }
    public void show() {
        viewDetail.show(studentModel);
    }
    public void setStudentName(String name) {
        studentModel.setName(name);
    }
    public String getStudentName(String name) {
        return studentModel.getName();
    }
    public void setStudentGender(String gender) {
        studentModel.setGender(gender);
    }
    public String getStudentGender(String gender) {
        return studentModel.getGender();
    }
}
