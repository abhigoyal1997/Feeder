package com.feeder.spartans.Feeder31;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.feeder.spartans.feederapp.R;

import java.util.ArrayList;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
//package com.feeder.spartans.feederapp;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class DeadlineAdapter extends ArrayAdapter<Deadline> {
    Context context;
    int layoutResourceId;
    ArrayList<Deadline> deadlines;
    LayoutInflater inflater;

    public DeadlineAdapter(Context context, int resource, @Nullable ArrayList<Deadline> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        deadlines = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Log.i("message", objects[0].getCoursename());

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DeadlineHolder deadline;

        if (row == null) {
            deadline = new DeadlineHolder();
            row = inflater.inflate(layoutResourceId, null);

            deadline.deadline_title = (TextView) row.findViewById(R.id.deadlineTitle);
//            deadline.deadline_title.setHeight(180);
//            deadline.deadline_title.setMinHeight(180);
            deadline.deadline_course = (TextView) row.findViewById(R.id.deadlineCourse);
//            deadline.deadline_title.setHeight(180);
//            deadline.deadline_course.setMinHeight(180);
//  deadline


            row.setTag(deadline);
        } else {
            deadline = (DeadlineHolder) row.getTag();
        }
        if (deadlines != null) {
            Deadline deadline_ele = deadlines.get(position);
            if (deadline != null) {
                deadline.deadline_title.setText(deadline_ele.getName());
                deadline.deadline_course.setText(deadline_ele.getCoursecode());
            }
        }
        return row;
    }

    static class DeadlineHolder {
        TextView deadline_title;
        TextView deadline_course;
    }
}