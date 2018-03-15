package com.whyuan.gson;


public class Exam {
    private String subject;
    private double grade;

    public Exam(String subject,double grade){
        this.subject=subject;
        this.grade=grade;
    }

    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
        subject = subject;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "subject='" + subject + '\'' +
                ", grade=" + grade +
                '}';
    }
}
