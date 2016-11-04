package com.feeder.spartans.feeder;

import java.util.List;

/**
 * Created by chitwan saharia on 11/3/2016.
 */
public class Courses {

    private String Course_name;
    private String Course_code;
    private String Course_credits;
    private List<Deadline> deadlines;

    public String getCourse_name() {
        return Course_name;
    }

    public void setCourse_name(String course_name) {
        Course_name = course_name;
    }



    public String getCourse_code() {
        return Course_code;
    }

    public void setCourse_code(String course_code) {
        Course_code = course_code;
    }

    public String getCourse_credits() {
        return Course_credits;
    }

    public void setCourse_credits(String course_credits) {
        Course_credits = course_credits;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }
}
