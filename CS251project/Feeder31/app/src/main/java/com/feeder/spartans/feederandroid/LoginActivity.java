package com.feeder.spartans.feederandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feeder.spartans.feederandroid.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               EditText username = (EditText)findViewById(R.id.username);
               EditText password = (EditText)findViewById(R.id.password);
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                sendPostRequest(uname,pass);

            }
        });


    }
    private void sendPostRequest(final String username,final String password)
    {
        new AsyncTask<String, Void, String>()
        {
            @Override
            protected void onPreExecute(){}

            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://192.168.0.107:8031/stud_login"); // here is your URL path
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
                    String resp = json.getString("name");
                    return resp;
                }
                catch(Exception e){
                    return new String("Internal Error: Cant Connect");
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