package com;

import java.io.*;
import java.util.*;

public class User implements Serializable {
    private static final long serialVersionUID = 10L;

    protected String userId;
    protected String firstName;
    protected String lastName;
    protected String emailAdd;
    protected String password;
    protected CourseList schedule;
    protected static final String PROGRESS_PATH = "data/progress/";
    protected HashMap<String, WorkBook> workBooks;
    protected ArrayList<String> submittedQuiz;

    public User(String firstName, String lastName, String emailAdd, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdd = emailAdd;
        this.password = password;
        schedule = new CourseList();
        workBooks = new HashMap<>();
        submittedQuiz = new ArrayList<>();
        // create random userId and check for duplication
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus(){
        if(userId.startsWith("p")){
            return "professor";
        }
        return "student";
    }

    public CourseList getSchedule(){
        return schedule;
    }

    public Set<String> getCourseIds(){return schedule.getCourseIds();}

    public void setSchedule(CourseList schedule){
        this.schedule = schedule;
    }

    public Course searchCourse(String courseId){
        return schedule.getCourse(courseId);
    }

    public boolean addCourseToSchedule(Course course){
        return schedule.insertCourse(course);
    }

    public boolean removeCourseFromSchedule(String courseId){
        return schedule.removeCourse(courseId);
    }


    public void startQuiz(String quizId) {
        workBooks.put(quizId, new WorkBook(userId, quizId));
    }

    public boolean isSubmittedQuiz(String quizId) {
        return submittedQuiz.contains(quizId);
    }

    public void submitQuiz(String quizId, ArrayList<Option> stuAnswers){
        WorkBook wb = workBooks.get(quizId);
        for(Option answer: stuAnswers){
            wb.addStuAnswer(answer);
        }
        submittedQuiz.add(quizId);
        wb.calGrade();
    }

    public String reviewQuiz(String quizId){
        return workBooks.get(quizId).toString();
    }

    public void saveProgress(String quizId){
        WorkBook workBook = workBooks.get(quizId);
        try{
            String directoryPath = PROGRESS_PATH + userId;
            new File(directoryPath).mkdirs();
            String filePath = PROGRESS_PATH + userId + '/' + quizId;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath), false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(workBook);
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public ArrayList<Option> loadProgress(String quizId) {
        try {
            String filePath = PROGRESS_PATH + userId + '/' + quizId;
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            WorkBook workBook =  (WorkBook) objectInputStream.readObject();
            workBooks.put(quizId, workBook);

            return workBook.getStuAnswers();
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
}
