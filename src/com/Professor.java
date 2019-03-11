package com;

import java.io.Serializable;
import java.util.*;

public class Professor extends User implements Serializable {
    private static final long serialVersionUID = 5L;
    public static final String STATUS = "professor";
    private String profId;

    public Professor(String firstName, String lastName, String emailAdd, String password){
        super(firstName, lastName, emailAdd, password);
        profId = "";
    }

    public void setProfId(String nextId){
        profId = "p" + nextId;
    }

    public String getProfId(){
        return profId;
    }

    public String getStatus(){
        return STATUS;
    }

    public Quiz createQuiz(String quizName, Date deadline, String instructions, int time){
        return new Quiz(quizName, deadline, instructions, time);
    }

    public Course createCourse(String courseName, String courseId){
        Course course = new Course(courseName, courseId, profId);
        addCourseToSchedule(course);
        return course;
    }

    public boolean removeStudent(String courseId, String stuId){
        return schedule.getCourse(courseId).removeStudent(stuId);
    }

    @Override
    public String toString(){
        String user = profId + "\t" + super.firstName.toUpperCase() + " " + super.lastName.toUpperCase() + "\n";
        user += "Email address: " + super.emailAdd + "\n";
        user += "Account Type: " + STATUS;
        return user;
    }
}
