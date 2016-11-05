package com.feeder.spartans.Feeder31;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates = new HashSet<>();
    private int radius;

    public EventDecorator(int color, int radius, HashSet<String> dates) {
        this.color = color;
        this.radius = radius;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        for (String date : dates) {
            Date d = null;
            try {
                d = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i("date", String.valueOf(d instanceof Date));
            this.dates.add(CalendarDay.from(d));
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ThreeDotSpans(color, radius));
    }
}
