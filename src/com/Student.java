package com;

import java.io.*;
import java.util.*;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 9L;
//    private static final String PROGRESS_PATH = "data/progress/";
//    private HashMap<String, WorkBook> workBooks;
//    private ArrayList<String> submittedQuiz;
    private String stuId;
    public static final String STATUS = "student";

    public Student(String firstName, String lastName, String emailAdd, String password){
        super(firstName, lastName, emailAdd, password);
        stuId = "";
//        workBooks = new HashMap<>();
//        submittedQuiz = new ArrayList<>();
    }

    public void setStuId(String nextId){
        stuId = "s" + nextId;
    }

    public String getStuId(){
        return stuId;
    }

    public String getStatus(){
        return STATUS;
    }
//
//    public void startQuiz(String quizId) {
//        workBooks.put(quizId, new WorkBook(stuId, quizId));
//    }
//
//    public boolean isSubmittedQuiz(String quizId) {
//        return submittedQuiz.contains(quizId);
//    }
//
//    public void submitQuiz(String quizId, ArrayList<Option> stuAnswers){
//        WorkBook wb = workBooks.get(quizId);
//        for(Option answer: stuAnswers){
//            wb.addStuAnswer(answer);
//        }
//        submittedQuiz.add(quizId);
//        wb.calGrade();
//    }

//    public String reviewQuiz(String quizId){
//        return workBooks.get(quizId).toString();
//    }

    @Override
    public String toString(){
        String user = stuId + "\t" + super.firstName.toUpperCase() + " " + super.lastName.toUpperCase() + "\n";
        user += "Email address: " + super.emailAdd + "\n";
        user += "Account Type: " + STATUS;
        return user;
    }

    public String stuNameId(){
        return stuId + "\t" + super.firstName + " " + super.lastName + "\n";
    }

    //TODO: All commented lines are moved to User so that both Professor and Student can take quiz
//    public void saveProgress(String quizId){
//        WorkBook workBook = workBooks.get(quizId);
//        try{
//            String directoryPath = PROGRESS_PATH + stuId;
//            new File(directoryPath).mkdirs();
//            String filePath = PROGRESS_PATH + stuId + '/' + quizId;
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath), false);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(workBook);
//        } catch (IOException e){
//            System.out.println(e);
//        }
//    }
//
//    public ArrayList<Option> loadProgress(String quizId) {
//        try {
//            String filePath = PROGRESS_PATH + stuId + '/' + quizId;
//            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            WorkBook workBook =  (WorkBook) objectInputStream.readObject();
//            workBooks.put(quizId, workBook);
//
//            return workBook.getStuAnswers();
//        } catch (Exception e) {
//            System.out.println(e);
//            return new ArrayList<>();
//        }
//    }
}
