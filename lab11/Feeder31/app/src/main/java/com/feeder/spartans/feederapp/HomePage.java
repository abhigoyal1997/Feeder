package com.feeder.spartans.feederapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class HomePage extends Fragment implements OnDateSelectedListener {
    SessionManager session;
    HashMap<String, ArrayList<Deadline>> mMap;
    ListView events;
    View view;
    MaterialCalendarView calender;
//    View emptyListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        events = (ListView) view.findViewById(R.id.deadline_list);
        session = new SessionManager(getActivity());
        mMap = new HashMap<String, ArrayList<Deadline>>();
        calender = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();
        calender.setCurrentDate(cal);
        calender.setSelectedDate(cal);
        calender.setOnDateChangedListener(this);
//        emptyListView = inflater.inflate(R.layout.empty_list_view, null);

        Bundle b = this.getArguments();
        if (b.getSerializable("deadline_map") != null)
            mMap = (HashMap<String, ArrayList<Deadline>>) b.getSerializable("deadline_map");


        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();
        HashSet<String> set3 = new HashSet<>();


        for (Map.Entry<String, ArrayList<Deadline>> deadline : mMap.entrySet()) {
            for (Deadline d : deadline.getValue()) {
                if (d.getName().equals("FeedBack")) {

                    set1.add(deadline.getKey());
                } else if (d.getName().equals("Assignment")) {
                    set2.add(deadline.getKey());
                } else if (d.getName().equals("Examination")) {
                    set3.add(deadline.getKey());
                }
            }
        }

        calender.addDecorator(new SelectorDecorator(getActivity()));
        calender.addDecorator(new EventDecorator(Color.BLUE, 5, set1));
        calender.addDecorator(new EventDecorator(Color.GRAY, 5, set2));
        calender.addDecorator(new EventDecorator(Color.YELLOW, 5, set3));
        this.onStart();
        return view;
    }

    @Override
    public void onStart() {
        CalendarDay selectedDate = calender.getSelectedDate();
        onDateSelected(calender, selectedDate, true);
        super.onStart();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        Date dateselect = date.getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");


        String formatted = format1.format(dateselect.getTime());


        final ArrayList<Deadline> list = mMap.get(formatted.toString());
        if (list != null) {
//        final  Deadline[] list = deadline_list.toArray(new Deadline[0]);
            events.setVisibility(View.VISIBLE);
            view.findViewById(R.id.empty).setVisibility(View.GONE);
//            calender.setTileHeightDp(50);
            DeadlineAdapter adapter = new DeadlineAdapter(getActivity(), R.layout.deadline_list_view, list);
//            events.setEmptyView(emptyListView);
            events.setAdapter(adapter);
            events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (list.get(position).getName().equals("FeedBack")) {
                        Feedback feedback = list.get(position).getFeedback_form();
                        if (feedback.getHasFilled() == Boolean.TRUE) {
                            Toast.makeText(getActivity(), "Already Filled",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Bundle b = new Bundle();
                            b.putSerializable("feedback", feedback);
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction f = fm.beginTransaction();
                            FeedBackform h = new FeedBackform();
                            h.setArguments(b);
                            f.addToBackStack(null);
                            f.replace(R.id.maincontent, h);
                            f.addToBackStack(null);
                            f.commit();
                        }
                    } else {
                        Bundle b = new Bundle();
                        b.putSerializable("deadline", list.get(position));
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction f = fm.beginTransaction();
                        DeadlineDetails h = new DeadlineDetails();
                        h.setArguments(b);
                        f.addToBackStack(null);
                        f.replace(R.id.maincontent, h);
                        f.addToBackStack(null);
                        f.commit();

                    }

                }
            });

        } else {
//
            events.setVisibility(View.GONE);
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
//     calender.setTileHeightDp(100);
        }
    }
}
