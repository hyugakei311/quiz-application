package com;

import java.io.Serializable;
import java.util.*;

public class WorkBook implements Serializable {
    private static final long serialVersionUID = 11L;

    private String studId;
    private String quizId;
    private ArrayList<Option> stuAnswers;

    public WorkBook(String stuId, String quizId){
        this.studId = stuId;
        this.quizId = quizId;
    }

    public String getStudId() {
        return studId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void addStuAnswer(Option option){
        stuAnswers.add(option);
    }

    public ArrayList<Option> getStuAnswers() {
        return stuAnswers;
    }

    public double calGrade() {
        // iterate through options and calculate the grade
        double grade = 0.0;
        for (Option op : stuAnswers) {
            grade += op.getPoint();
        }
        return grade;
    }

    public String toString(Student student){
        String workbook = "";
        for(Option op : stuAnswers){
            workbook += op.getOptionId() + " " + op.getText();
        }
        workbook += "Grade: " + calGrade();
        return workbook;
    }
}
