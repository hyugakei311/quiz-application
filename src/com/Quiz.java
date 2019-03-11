package com;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Quiz implements Serializable {
    private static final long serialVersionUID = 7L;

    private String quizId;
    private ArrayList<String> courseIds;
    private Date deadline;
    private String instructions;
    private String quizName;
    private HashSet<Question> questions;
    private int nextQuestionId;
    private int nextId;
    private int time; // in seconds

    public Quiz() {
        courseIds = new ArrayList<>();
        questions = new HashSet<>();
        quizId = "";
        nextQuestionId = 1;
        // TODO: use deadline to check whether the user is enable to review the quiz.
        this.deadline = new Date();
        this.instructions = "";
        this.quizName = "";
        this.time = 0;//in seconds
    }

    public Quiz(String quizName, Date deadline, String instructions, int time){
        courseIds = new ArrayList<>();
        questions = new HashSet<>();
        quizId = "";
        nextQuestionId = 1;
        // TODO: use deadline to check whether the user is enable to review the quiz.
        this.deadline = deadline;
        this.instructions = instructions;
        this.quizName = quizName;
        this.time = time;//in seconds
    }

    public String getQuizId() {
        return quizId;
    }

    public String setQuizId(String nextId){
        quizId = "Quiz " + nextId;
        return quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void addCourseId(String courseId) {
        courseIds.add(courseId);
    }

    public void removeCourseId(String courseId) {
        courseIds.remove(courseId);
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getTime() {
        return time;
    }

    public HashSet<Question> getQuestions() {
        return questions;
    }
//
//    public void setQuestions(HashSet<Question> questions) {
//        this.questions = questions;
//    }

    public void setTime(int timeLimit) {
        time = timeLimit * 60;
    }

    public Question addQuestion(Question question){
        question.setQuestionId(Integer.toString(nextQuestionId));
        questions.add(question);
        nextQuestionId++;
        return question;
    }

    public void finalizeQuiz(){
        for(Question question : questions){
            question.finalizeQuestion();
        }
    }

    public String getDeadlineInstruction(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String quiz = "This quiz is due at " + simpleDateFormat.format(deadline.getTime()) + ".\n";
        quiz += instructions;
        return quiz;
    }

    public String toString(){
        return quizId + "\t" + quizName + "\n";
    }
}
