package com;

import java.io.Serializable;

public class Option implements Serializable {
    private static final long serialVersionUID = 4L;

    private String optionId;
    private String questionId;
    private String text;
    private double point;
    private boolean correct = false;

    public Option(String text, boolean correct) {
        this.text = text;
        questionId = "";
        point = 0;
        this.correct = correct;
        optionId = "";
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setOptionId(String nextId){
        this.optionId =  nextId;
    }

    public String getOptionId() {
        return optionId;
    }

    public String getQuestionId(){return questionId;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPoint(){return point;}

    public void setPoint(double point){
        this.point = point;
    }

    public boolean isCorrect(){
        return correct;
    }

    public void changeCorrectness() {
        if (isCorrect()){
            correct = false;
        } else{
            correct = true;
        }
    }

    public String toString(){
        return questionId + "." + optionId + "\t" + text + "\n";
    }
}
