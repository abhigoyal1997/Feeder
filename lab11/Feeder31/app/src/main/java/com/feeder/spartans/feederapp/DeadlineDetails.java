package com.feeder.spartans.feederapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class DeadlineDetails extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deadline_details, container, false);
        TextView course_code = (TextView) view.findViewById(R.id.course_code);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        Bundle b = this.getArguments();
        Deadline deadline = new Deadline();
        if(b.getSerializable("deadline") != null)
            deadline = (Deadline) b.getSerializable("deadline");
        course_code.setText(deadline.getCoursecode());
        date.setText(deadline.getDate());
        name.setText(deadline.getName());
        desc.setText(deadline.getDesc());

        return view;
    }
}