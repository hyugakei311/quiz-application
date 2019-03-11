package com;

import java.io.*;
import java.util.*;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SYSTEM_DATA_PATH = "data/system/";
    public static final String SYSTEM_FILE = "SYSTEM_FILE";
    private static final String STUDENT_REGEX = "[A-Za-z]+\\.[A-Za-z]+@my\\.columbiasc\\.edu";
    private static final String PROFESSOR_REGEX = "[A-Za-z]+@columbiasc\\.edu";

    private User currentUser;
    private Quiz currentQuiz;
    private Course currentCourse;

    private HashMap<String, Professor> profList;
    private HashMap<String, Student> stuList;
    private CourseList courseCatalog;
    private QuizList quizCatalog;
    private int nextProfId;
    private int nextStuId;

    public Admin(){
        profList = new HashMap<>();
        stuList = new HashMap<>();
        courseCatalog = new CourseList();
        quizCatalog = new QuizList();
        nextProfId = 10000;
        nextStuId = 10000;
    }

    public Set<String> getCourseCatalog(){
        return courseCatalog.getCourseIds();
    }

    public User logIn(String emailAdd, String password){
        // check logIn credentials
        for (User student : stuList.values()) {
            if (student.getEmailAdd().equals(emailAdd) && student.getPassword().equals(password)) {
                currentUser = student;
                return currentUser;
            }
        }
        for (User professor : profList.values()) {
            if (professor.getEmailAdd().equals(emailAdd) && professor.getPassword().equals(password)) {
                currentUser = professor;
                return currentUser;
            }
        }
        return null;
    }

    public void logOut(){
        currentUser = null;
        //codes to save progress to file
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    public Course getCurrentCourse(){
        return currentCourse;
    }

    public Quiz getCurrentQuiz(){
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz quiz) {
        currentQuiz = quiz;
    }

    public User addUser(String firstName, String lastName, String emailAdd, String password){
        // create a User with correct accType
        User newUser = null;
        if (emailAdd.matches(STUDENT_REGEX)){
            // create a Student User and add to stuList
            addStudent(firstName, lastName, emailAdd, password);
        } else if (emailAdd.matches(PROFESSOR_REGEX)) {
            // create a Prof User and add to profList
            addProfessor(firstName, lastName, emailAdd, password);
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
        return newUser;
    }


    public Professor getProfessor(String profId){
        return profList.get(profId);
    }

    public boolean addProfessor(String firstName, String lastName, String emailAdd, String password){
        Professor prof = new Professor(firstName, lastName, emailAdd, password);
        prof.setProfId(Integer.toString(nextProfId));
        nextProfId++;
        return (profList.put(prof.getProfId(), prof) != null);
    }

    public Student getStudent(String stuId){
        return stuList.get(stuId);
    }

    public boolean addStudent(String firstName, String lastName, String emailAdd, String password){
        Student stu = new Student(firstName, lastName, emailAdd, password);
        stu.setStuId(Integer.toString(nextStuId));
        nextStuId++;
        return (stuList.put(stu.getStuId(), stu) != null);
    }

    public boolean removeStudent(String profId, String courseId, String stuId){
        Professor prof = getProfessor(profId);
        if (prof != null){
            return prof.removeStudent(courseId, stuId);
        }
        return false;
    }

    public Course getCourse(String courseId){
        return courseCatalog.getCourse(courseId);
    }

    public void addCourse(Course course){
        courseCatalog.insertCourse(course);
    }

    public ArrayList<String> searchCourse(String keyword){
        return courseCatalog.searchCourse(keyword);
    }

    public boolean removeCourse(String courseId){
        return courseCatalog.removeCourse(courseId);
    }

    public boolean addCourseToSchedule(Course course){
        return currentUser.addCourseToSchedule(course);
    }

    public void rmCourseFromSchedule(String courseId){
        currentUser.removeCourseFromSchedule(courseId);
    }

    public Quiz getQuiz(String quizId){
        return quizCatalog.getQuiz(quizId);
    }

    public boolean addQuiz(Quiz quiz){ //for "Done" button
        quiz.finalizeQuiz();
        return quizCatalog.insertQuiz(quiz);
    }

    public boolean addQuizToCourse(String courseId, String quizId){
        return getCourse(courseId).addQuiz(quizId);
    }

    public boolean rmQuizFrCourse(String courseId, String quizId){
        return getCourse(courseId).removeQuiz(quizId);
    }

//    public Quiz createQuiz(String profId, String quizName, Calendar deadline, String instructions, int time){
//        Professor prof = (Professor) getProfessor(profId);
//        if (prof != null){
//            Quiz quiz = prof.createQuiz(quizName, deadline, instructions, time);
//            return quiz;
//        }
//        return null;
//    }

//    public void addOptionToQuestion(String profId, Question question, String optionText, boolean correct){
//        Professor prof = (Professor) getProfessor(profId);
//        if (prof != null){
//            question.addOption(optionText, correct);
//        }
//    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(SYSTEM_DATA_PATH + SYSTEM_FILE, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);

            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
