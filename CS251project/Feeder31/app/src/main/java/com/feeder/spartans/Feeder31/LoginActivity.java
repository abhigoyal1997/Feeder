package com.feeder.spartans.Feeder31;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feeder.spartans.feederapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chitwan saharia on 11/3/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private  SessionManager session ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        session = new SessionManager(getApplicationContext());
        Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                sendPostRequest(uname,pass,session);


            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
            System.exit(0);

        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }
    }
    private void sendPostRequest(final String username,final String password,final SessionManager session)
    {
        new AsyncTask<String, Void, String>()
        {
            @Override
            protected void onPreExecute(){}

            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://10.0.2.2:8031/stud_login"); // here is your URL path
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("username", username);
                    postDataParams.put("password", password);
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
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    JSONObject json = new JSONObject(response.toString());
                    String name = json.getString("name");
//                    return name;
                    if(!name.equals("Does Not Exist")) {
                        String uname = json.getString("uname");
                        session.createLoginSession(name, uname);
                        Intent i = new Intent(getApplicationContext(), StudentHomePage.class);
                        startActivity(i);
//                        finish();
                        return "Successful Login";
                    }
                    else
                    {
                        return "Bad Login Credentials!!";
                    }

                }
                catch(Exception e){
                    return e.toString();
                }

            }
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
            }
        }.execute();
    }


}
