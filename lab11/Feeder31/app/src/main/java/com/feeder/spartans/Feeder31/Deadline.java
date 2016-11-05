package com.feeder.spartans.Feeder31;

import java.io.Serializable;

/**
 * Created by chitwan saharia on 11/3/2016.
 */
public class Deadline implements Serializable{

    private String name;
    private String coursecode;
    private String desc;
    private String date;
    private Feedback feedback_form;

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Feedback getFeedback_form() {
        return feedback_form;
    }

    public void setFeedback_form(Feedback feedback_form) {
        this.feedback_form = feedback_form;
    }
}
