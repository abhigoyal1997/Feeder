package com.feeder.spartans.feederapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class FeedBackform extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);
        Bundle b = this.getArguments();
        Feedback feedback = new Feedback();
        if(b.getSerializable("feedback") != null)
            feedback = (Feedback) b.getSerializable("feedback");
        displayFeedback(feedback);
        return view;
}
    public void displayFeedback(Feedback feedback)
    {
        for(Question question : feedback.getQuestions())
        {
            if(question.getQuestion_type().equals("TF"))
            {

            }
            if(question.getQuestion_type().equals("RB"))
            {

            }
            if(question.getQuestion_type().equals("DD"))
            {

            }
            if(question.getQuestion_type().equals("CB"))
            {

            }
            if(question.getQuestion_type().equals("MCQ"))
            {

            }
        }
    }
}
