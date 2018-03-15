package com.whyuan.gson;

public class Person {
    private String name;
    private int age;
    private Exam exam;

    public Person(String name,int age){
        this.name=name;
        this.age=age;

    }
    public Person(String name,int age,Exam exam){
        this.name=name;
        this.age=age;
        this.exam=exam;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", exam=" + exam +
                '}';
    }
}
