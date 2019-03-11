package com;

import java.io.Serializable;
import java.util.*;

public class Question implements Serializable {
    private static final long serialVersionUID = 6L;

    private String questionId;
    private String questionText;
    private HashMap<String,Option> options;
    private int numCorrectOption;
    private double point;
    private int nextOptionId;

    public Question(String questionText, double point) {
        this.questionText = questionText;
        this.point = point;
        options = new HashMap<>();
        questionId = "";
        nextOptionId = 1;
    }

    public void setQuestionId(String nextId){
        this.questionId = "Q" + nextId;
    }

    public void addOption(Option option){
        option.setOptionId(Integer.toString(nextOptionId));
        nextOptionId++;
        options.put(option.getOptionId(), option);
        if (option.isCorrect()) {
            numCorrectOption ++;
        }
    }

    public void removeOption(String optionId) {
        Option option = options.remove(optionId);
        if (option.isCorrect()) {
            numCorrectOption --;
        }
    }

    public Option getOption(String optionId){
        return options.get(optionId);
    }

    public Collection<Option> getOptions(){return options.values();}

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getNumCorrectOption(){return numCorrectOption;}

    public void setPointPerOption(){
        double optionPoint = point / numCorrectOption;
        for(Option option : options.values()){
            if(option.isCorrect()){
                option.setPoint(optionPoint);
            }
        }
    }

    public void finalizeQuestion() {
        // TODO: invoke this function when user hits done button.
        setPointPerOption();
    }

    public String toString() {
        return questionId + ". " + questionText + " (" + point + "pt.)" + "\n";
    }

//    public String toStringOptions(){
//        String optionList = "";
//        for(String opId : options.keySet()){
//            optionList += "\t" + options.get(opId) + "\n";
//        }
//        return optionList;
//    }
}
