package com;

import java.io.Serializable;
import java.util.*;

public class QuizList implements Serializable {
    private static final long serialVersionUID = 8L;

    private HashMap<String,Quiz> quizList;
    private int nextQuizId;

    public QuizList() {
        quizList = new HashMap<>();
        nextQuizId = 1;
    }

    public Quiz getQuiz(String quizId){
        return quizList.get(quizId);
    }

    public Collection<Quiz> getQuizzes(){return quizList.values();}

    public Set<String> getQuizzesId(){return quizList.keySet();}

    public Quiz searchQuiz(String quizId){
        if(hasQuiz(quizId)) {
            for (String id : quizList.keySet()) {
                if (id.equals(quizId)) {
                    return getQuiz(quizId);
                }
            }
        }
        return null;
    }

    public boolean hasQuiz(String quizId) {
        return quizList.containsKey(quizId);
    }

    public boolean insertQuiz(Quiz quiz){
        String quizId = quiz.setQuizId(Integer.toString(nextQuizId));
        nextQuizId++;
        quizList.put(quizId, quiz);
        return true;
    }

    public boolean removeQuiz(String quizId){
        return (quizList.remove(quizId) != null);
    }

}
