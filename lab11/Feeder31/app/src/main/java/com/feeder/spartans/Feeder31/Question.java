package com.feeder.spartans.Feeder31;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class Question implements Serializable{
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    String question;
    Integer qid;
    String question_type;
    List<String> options;
}
