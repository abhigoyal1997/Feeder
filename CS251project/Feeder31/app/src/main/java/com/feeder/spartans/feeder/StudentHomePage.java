package com.feeder.spartans.feeder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StudentHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Courses> courselist;
    private SessionManager session;
    private CaldroidFragment caldroidFragment;
    private HashMap<String, ArrayList<Deadline>> datemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        courselist = new ArrayList<>();
        caldroidFragment = new CaldroidFragment();
        datemap = new HashMap<>();
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn())
        {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
        else
        {
            getDeadlineData(session.getuName());
        }

        Bundle args = new Bundle();

        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calender1, caldroidFragment);

        t.commit();
        final CaldroidListener listener = new CaldroidListener() {
            android.text.format.DateFormat formatter = new android.text.format.DateFormat();

            @Override
            public void onSelectDate(Date date, View view) {

                caldroidFragment.refreshView();
                CharSequence strdate = formatter.format("yyyy-MM-dd", date);
                final ArrayList<Deadline> deadline_list = datemap.get(strdate.toString());
                if (deadline_list != null) {
                   final  Deadline[] list = deadline_list.toArray(new Deadline[0]);
                    DeadlineAdapter adapter = new DeadlineAdapter(getApplicationContext(), R.layout.deadline_list_view, list);
                    ListView listview = (ListView) findViewById(R.id.event);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            if(list[position].getName().equals("FeedBack"))
                            {
                                Toast.makeText(getApplicationContext(), "Peace",
                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                            }

                        }
                    });

                } else {

                    Toast.makeText(getApplicationContext(), "Peace",
                            Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format("yyyy-MM-dd", date),
                        Toast.LENGTH_SHORT).show();
            }


        };

        caldroidFragment.setCaldroidListener(listener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void getDeadlineData(final String uname) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {

            }

            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://192.168.0.107:8031/stud_home"); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("username", uname);
//                    postDataParams.put("password", password);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                    writer.write(postDataParams.toString());
                    writer.close();
                    InputStream is = conn.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    String resString;
                    resString = response.toString();
                    session.createDatabase(resString);
                    populate(resString);
                    return "Successful Data fetch";


                } catch (Exception e) {
                    if(session.getDatabase() != null)
                    {
                        populate(session.getDatabase());
                        return("Internet Sucks!! Loading Cache Copy");
                    }
                    return "Oops!! Something Unexpected Occured";
                }

            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
    public void populate(String resString)
    {
        try{
        JSONObject json = new JSONObject(resString);
        JSONArray jArray = json.getJSONArray("courses");
        ArrayList<Courses> course_list = new ArrayList<Courses>();
        for (int m = 0; m < jArray.length(); m++) {
            JSONObject j = jArray.getJSONObject(m);
            Courses course = new Courses();
            course.setCourse_name(j.getString("course_name"));
            course.setCourse_code(j.getString("course_code"));
            ArrayList<Deadline> deadlines = new ArrayList<Deadline>();
            JSONArray j2 = j.getJSONArray("deadlines");
            for (int i = 0; i < j2.length(); i++) {
                JSONObject obj = j2.getJSONObject(i);
                Deadline deadline = new Deadline();
                deadline.setName(obj.getString("name"));
                deadline.setDesc(obj.getString("description"));
                deadline.setDate(obj.getString("date"));
//                            Log.i("date",deadline.getDate());

                deadline.setCoursecode(course.getCourse_code());
                if (deadline.getName().equals("Feedback")) {
                    Feedback feedback = new Feedback();
                    feedback.setName(obj.getString("feedbackname"));
                    List<String> quest = new ArrayList<String>();
                    List<String> questtype = new ArrayList<String>();
                    JSONArray question = obj.getJSONArray("questionset");
                    for (int k = 0; k < question.length(); k++) {
                        JSONObject q = question.getJSONObject(i);
                        quest.add(q.getString("question"));
                        questtype.add(q.getString("question_type"));
                    }
                    feedback.setQuestions(quest);
                    feedback.setQuestion_type(questtype);
                    deadline.setFeedback_form(feedback);
                } else {
                    deadline.setFeedback_form(null);
                }
                ArrayList<Deadline> temp = datemap.get(deadline.getDate());
                if (temp != null)
                    temp.add(deadline);
                else {
                    temp = new ArrayList<Deadline>();
                    temp.add(deadline);
                }

                datemap.put(deadline.getDate(), temp);
            }
            course.setDeadlines(deadlines);
            course_list.add(course);
        }}
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Courses",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            session.logoutUser();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);

        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.courses) {




        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

class DeadlineAdapter extends ArrayAdapter<Deadline> {
    Context context;
    int layoutResourceId;
    Deadline deadlines[] = null;

    public DeadlineAdapter(Context context, int resource, Deadline[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        deadlines = objects;
//        Log.i("message", objects[0].getCoursename());

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DeadlineHolder deadline = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            deadline = new DeadlineHolder();
            deadline.deadline_title = (TextView) row.findViewById(R.id.deadlineTitle);
            deadline.deadline_title.setHeight(200);
            deadline.deadline_title.setMinHeight(200);
            deadline.deadline_course = (TextView) row.findViewById(R.id.deadlineCourse);
            deadline.deadline_title.setHeight(200);
            deadline.deadline_course.setMinHeight(200);
//  deadline


            row.setTag(deadline);
        } else {
            deadline = (DeadlineHolder) row.getTag();
        }

        Deadline deadline_ele = deadlines[position];
        deadline.deadline_title.setText(deadline_ele.getName());
        deadline.deadline_course.setText(deadline_ele.getCoursecode());
        return row;
    }

    static class DeadlineHolder {
        TextView deadline_title;
        TextView deadline_course;
    }


}