package com.feeder.spartans.Feeder31;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.feeder.spartans.feederapp.R;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class CourseAdapter extends ArrayAdapter<Courses> {
    Context context;
    int layoutResourceId;
    Courses courses[] = null;

    public CourseAdapter(Context context, int resource, Courses[] objects) {
        super(context,resource,objects);
        this.context = context;
        this.layoutResourceId = resource;
        courses = objects;
//        Log.i("message", objects[0].getCoursename());
 }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CourseHolder course = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            course = new CourseHolder();
            course.course_code = (TextView) row.findViewById(R.id.course_code);
//            deadline.deadline_title.setHeight(180);
//            deadline.deadline_title.setMinHeight(180);
            course.course_name = (TextView) row.findViewById(R.id.course_name);
            course.course_credits = (TextView) row.findViewById(R.id.course_credits);
//            deadline.deadline_title.setHeight(180);
//            deadline.deadline_course.setMinHeight(180);
//  deadline


            row.setTag(course);
        } else {
            course = (CourseHolder) row.getTag();
        }

        Courses course_ele = courses[position];
        course.course_code.setText(course_ele.getCourse_code());
        course.course_name.setText(course_ele.getCourse_name());
        course.course_credits.setText(course_ele.getCourse_credits());
        return row;
    }

    static class CourseHolder {
        TextView course_code;
        TextView course_name;
        TextView course_credits;
    }
}