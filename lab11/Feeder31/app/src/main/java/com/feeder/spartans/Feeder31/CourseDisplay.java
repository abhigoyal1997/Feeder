package com.feeder.spartans.Feeder31;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feeder.spartans.feederapp.R;

import java.util.ArrayList;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class CourseDisplay extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_display, container, false);
        Bundle b = this.getArguments();
        ArrayList<Courses> course_list = new ArrayList<>();
        if (b.getSerializable("coursesinfo") != null)
            course_list = (ArrayList<Courses>) b.getSerializable("coursesinfo");
        if (course_list != null) {
            final Courses[] list = course_list.toArray(new Courses[0]);
            CourseAdapter adapter = new CourseAdapter(getActivity(), R.layout.course_list, list);
            ListView listdisp = (ListView) view.findViewById(R.id.course_list);
            listdisp.setAdapter(adapter);


        }
        return view;
    }
}
