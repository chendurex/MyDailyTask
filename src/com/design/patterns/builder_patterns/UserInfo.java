package com.design.patterns.builder_patterns;

/**
 * Created by LENOVO on 2016/3/18.
 */
public class UserInfo {
    private String name;
    private Integer age;
    private Integer gender;
    private String email;
    private String school;

    public UserInfo(String name,Integer age,Integer gender,String email,String school) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public static BuilderHolder builder() {
        return new BuilderHolder();
    }

    public static class BuilderHolder {
        private String name;
        private Integer age;
        private Integer gender;
        private String email;
        private String school;

        public BuilderHolder name(String name) {
            this.name = name;
            return this;
        }
        public BuilderHolder age(Integer age) {
            this.age = age;
            return this;
        }
        public BuilderHolder gender(Integer gender) {
            this.gender = gender;
            return this;
        }
        public BuilderHolder email(String email) {
            this.email = email;
            return this;
        }
        public BuilderHolder school(String school) {
            this.school = school;
            return this;
        }
        public UserInfo create() {
            return new UserInfo(name,age,gender,email,school);
        }
    }

    @Override
    public String toString() {
        return "name="+name+",and age="+age+",email="+email+",gender="+gender+",school="+school;
    }

    public static void main(String[] args) {
        System.out.println(UserInfo.builder().email("3232").name("hhaa").age(11).create());
    }
}
