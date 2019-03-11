package com;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private static final long serialVersionUID = 2L;

    private String courseId;
    private String profId;
    private String courseName;
    private ArrayList<String> quizIdList;
    private ArrayList<String> roll;

    public Course(String courseName, String courseId, String profId) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.profId = profId;
        quizIdList = new ArrayList<>();
        roll = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId(){
        return courseId;
    }

    public ArrayList<String> getQuizzes(){
        return quizIdList;
    }

    public ArrayList<String> getRoll(){
        return roll;
    }

    public String getProfessorId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }

    public boolean addQuiz(String quizId){
        return quizIdList.add(quizId);
    }

    public boolean removeQuiz(String quizId){
        return quizIdList.remove(quizId);
    }

    public boolean addStudentToRoll(String userId){
        return roll.add(userId);
    }

    public boolean removeStudent(String userId){
        return roll.remove(userId);
    }

    public String toString(Professor prof) {
        String course = courseId + " " + courseName + "\n";
        course += "Professor: " + prof.getFirstName() + " " + prof.getLastName();
        return course;
    }

    public String toString(){
        return courseId + "\n" + courseName + "\n";
    }
}
