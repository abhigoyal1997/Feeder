package com.feeder.spartans.Feeder31;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chitwan saharia on 11/5/2016.
 */
public class FeedBackform extends Fragment {
    View view;
    HashMap<Integer,View> Viewmap;
    SessionManager session;
    Feedback feedback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feedback_form,container,false);
        Bundle b = this.getArguments();
        Viewmap = new HashMap<>();
        session = new SessionManager(getActivity());
         feedback = new Feedback();
        if(b.getSerializable("feedback") != null)
            feedback = (Feedback) b.getSerializable("feedback");
        displayFeedback(feedback);
        return view;
}
    public void displayFeedback(final Feedback feedback) {
        LinearLayout l = (LinearLayout) view.findViewById(R.id.form);
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        TextView tv1 = new TextView(getActivity());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(display.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        tv1.setText(feedback.getName());
        LinearLayout.LayoutParams ques = new LinearLayout.LayoutParams(display.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        ques.setMargins(10, 30, 10, 30);
        tv1.setTextSize(35);
        l.addView(tv1, lp);
        for (Question question : feedback.getQuestions()) {
            if (question.getQuestion_type().equals("TF")) {

                TextView tv = new TextView(getActivity());
                tv.setText(question.getQuestion());
                tv.setMinHeight(20);


                l.addView(tv, ques);
                EditText et = new EditText(getActivity());
                et.setId(question.getQid());
//                ViewGroup.LayoutParams  = new ViewGroup.LayoutParams(display.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                l.addView(et, ques);
                Viewmap.put(question.getQid(), et);

            }
            if (question.getQuestion_type().equals("RB")) {
                TextView tv = new TextView(getActivity());
                tv.setText(question.getQuestion());
                l.addView(tv, ques);
                RadioGroup rg = new RadioGroup(getActivity());
                rg.setOrientation(RadioGroup.HORIZONTAL);
                rg.setId(question.getQid());
                int m = 0;
                rg.check(m + 1);
//                l.addView(rg,lp);
                for (int k = 0; k < 5; k++) {
                    RadioButton rb = new RadioButton(getActivity());
                    rb.setId(k + 1);
                    rb.setText(Integer.toString(k + 1));
                    rg.addView(rb);
                }
                l.addView(rg, ques);
                Viewmap.put(question.getQid(), rg);

            }
            if (question.getQuestion_type().equals("DD")) {
                TextView tv = new TextView(getActivity());
                tv.setText(question.getQuestion());
                l.addView(tv, ques);
                String[] strings = question.getOptions().toArray(new String[0]);
                Spinner spinner = new Spinner(getActivity());
                spinner.setId(question.getQid());
                spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings));
                l.addView(spinner);
                Viewmap.put(question.getQid(), spinner);
            }
            if (question.getQuestion_type().equals("MCQ")) {
                TextView tv = new TextView(getActivity());
                tv.setText(question.getQuestion());
                l.addView(tv, ques);
                RadioGroup rg = new RadioGroup(getActivity());
                rg.setOrientation(RadioGroup.VERTICAL);
                rg.setId(question.getQid());
//                l.addView(rg,lp);
                int m = 0;
                rg.check(m + 1);
                List<String> options = question.getOptions();
                for (int k = 0; k < question.getOptions().size(); k++) {
                    RadioButton rb = new RadioButton(getActivity());
                    rb.setId(k + 1);
                    rb.setText(options.get(k));
                    rg.addView(rb);
                }
                l.addView(rg, ques);
                Viewmap.put(question.getQid(), rg);
            }

        }
        Button bt = new Button(getActivity());
        bt.setText("Submit");
        l.addView(bt, ques);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject postDataParams = new JSONObject();
                Iterator it = Viewmap.entrySet().iterator();
                try {
                    postDataParams.put("username", session.getuName());

                    postDataParams.put("fid", Integer.toString(feedback.getId()));
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        if (pair.getValue() instanceof Spinner) {
                            Integer key = (Integer) pair.getKey();
                            Long val = ((Spinner) pair.getValue()).getSelectedItemId();
                            postDataParams.put("q" + Integer.toString(key), Long.toString(val));
                        }
                        if (pair.getValue() instanceof RadioGroup) {
                            Integer key = (Integer) pair.getKey();
                            Integer val = ((RadioGroup) pair.getValue()).getCheckedRadioButtonId();
                            postDataParams.put("q" + Integer.toString(key), Integer.toString(val));
                        }
                        if (pair.getValue() instanceof EditText) {

                            Integer key = (Integer) pair.getKey();
                            String val = ((EditText) pair.getValue()).getText().toString();
                            postDataParams.put("q" + Integer.toString(key), val);
                        }
                    }
                    formsubmitter(postDataParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
        public void formsubmitter(final JSONObject postDataParams )
        {
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                }

                protected String doInBackground(String... arg0) {

                    try {

                        URL url = new URL("http://10.0.2.2:8031/feedbackresponse"); // here is your URL path

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
                        return response.toString();

                    } catch (Exception e) {
                        return "Error !! Feedback Not Submitted";
                    }
                }
                @Override
                protected void onPostExecute(String result) {
                    Toast.makeText(getActivity(), result,
                            Toast.LENGTH_LONG).show();
                    killfragment();
                }

            }.execute();
        }
    public void killfragment()
    {
        String s = "\"hasfilled\":\"True\",\"feedbackid\":\""+ Integer.toString(feedback.getId()) +"\"";
        String s1 = "\"hasfilled\":\"False\",\"feedbackid\":\""+ Integer.toString(feedback.getId()) +"\"";
        String temp = session.getDatabase();
        temp = temp.replace(s1,s);
        session.createDatabase(temp);
        Intent i = new Intent(getActivity(), StudentHomePage.class);
        startActivity(i);

    }



}
