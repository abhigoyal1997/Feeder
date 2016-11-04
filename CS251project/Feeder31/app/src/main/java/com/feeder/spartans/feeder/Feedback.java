package com.feeder.spartans.feeder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chitwan saharia on 11/3/2016.
 */
public class Feedback implements Serializable{
    private String name;
    private List<String> questions;
    private List<Integer> qid;
    private List<String> question_type;
    private int id;
    private Boolean hasFIlled;

    public Boolean getHasFIlled() {
        return hasFIlled;
    }

    public void setHasFIlled(Boolean hasFIlled) {
        this.hasFIlled = hasFIlled;
    }

    public List<Integer> getQid() {
        return qid;
    }

    public void setQid(List<Integer> qid) {
        this.qid = qid;
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

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(List<String> question_type) {
        this.question_type = question_type;
    }
}
