package com.example.joachim.geoiquiz;

import android.content.ContentValues;

import org.w3c.dom.Comment;

public class Question {
    private int id;
    private String question;
    private String answer;

    public Question(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString(){
        //String[] array = {question, answer};

        return question;
    }



}
