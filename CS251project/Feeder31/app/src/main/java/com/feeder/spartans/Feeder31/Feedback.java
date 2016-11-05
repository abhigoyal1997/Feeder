package com.feeder.spartans.Feeder31;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chitwan saharia on 11/3/2016.
 */
public class Feedback implements Serializable{
    private String name;
   private List<Question> questions;

    private int id;
    private Boolean hasFilled;

    public Boolean getHasFilled() {
        return hasFilled;
    }

    public void setHasFilled(Boolean hasFilled) {
        this.hasFilled = hasFilled;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
