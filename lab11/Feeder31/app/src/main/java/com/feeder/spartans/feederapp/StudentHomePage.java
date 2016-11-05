package com.feeder.spartans.feederapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Courses> course_list;
    private SessionManager session;
    private HashMap<String, ArrayList<Deadline>> datemap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        course_list = new ArrayList<>();
        datemap = new HashMap<>();
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        } else {
            getDeadlineData(session.getuName());
            Bundle b = new Bundle();
            b.putSerializable("deadline_map",datemap);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            HomePage h = new HomePage();
            h.setArguments(b);
            f.replace(R.id.maincontent,h);
            f.addToBackStack(null);
            f.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.logout) {
            session.logoutUser();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.courses) {
            Bundle b = new Bundle();
            b.putSerializable("coursesinfo",course_list);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            CourseDisplay h = new CourseDisplay();
            h.setArguments(b);
            f.replace(R.id.maincontent,h);
            f.addToBackStack(null);
            f.commit();

        } else if (id == R.id.feedbacks) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    if (session.getDatabase() != null) {
                        populate(session.getDatabase());
                        return ("Internet Sucks!! Loading Cache Copy");
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

    public void populate(String resString) {
        try {
            JSONObject json = new JSONObject(resString);
            JSONArray jArray = json.getJSONArray("courses");
             course_list = new ArrayList<Courses>();
            for (int m = 0; m < jArray.length(); m++) {
                JSONObject j = jArray.getJSONObject(m);
                Courses course = new Courses();
                course.setCourse_name(j.getString("course_name"));
                course.setCourse_code(j.getString("course_code"));
                course.setCourse_credits(j.getString("credits"));
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
                        feedback.setId(Integer.parseInt(obj.getString("feedbackid")));
                        if(obj.getString("hasfilled").equals("True"))
                        {
                            feedback.setHasFilled(Boolean.TRUE);
                        }
                        else
                        {
                            feedback.setHasFilled(Boolean.FALSE);
                        }
                        List<Question> question_set = new ArrayList<>();
                        JSONArray question = obj.getJSONArray("questionset");
                        for (int k = 0; k < question.length(); k++) {
                            JSONObject q = question.getJSONObject(i);
                            Question ques = new Question();
                            ques.setQuestion(q.getString("question"));
                            ques.setQid(Integer.parseInt(q.getString("question_id")));
                            ques.setQuestion_type(q.getString("question_type"));
                            if(ques.getQuestion_type().equals("MCQ")||ques.getQuestion_type().equals("DD")||ques.getQuestion_type().equals("CB"))
                            {
                                JSONArray options = q.getJSONArray("options");
                                List<String>option_list = new ArrayList<>();
                                for(int h=0;h< options.length();h++)
                                {
                                    JSONObject optionobj = options.getJSONObject(h);
                                    option_list.add(optionobj.getString("option"));
                                }
                                ques.setOptions(option_list);
                            }
                            question_set.add(ques);
                        }
                        feedback.setQuestions(question_set);
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
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Courses",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "StudentHomePage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.feeder.spartans.feederapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "StudentHomePage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.feeder.spartans.feederapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}



