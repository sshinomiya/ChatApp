package com.example.sshinomiya.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.os.AsyncTask;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mInputMessage;
    private Button mSendMessage;
    private LinearLayout mMessageLog;
    TextView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mInputMessage = (EditText)findViewById(R.id.input_message);
        mSendMessage = (Button)findViewById(R.id.send_message);
        mMessageLog = (LinearLayout)findViewById(R.id.message_log);
        mSendMessage.setOnClickListener(this);
        this.web = (TextView)findViewById(R.id.textview_id);

        new HTTPRequest().execute();

    }

    @Override
    public void onClick(View v) {
        /*if (v.equals(mSendMessage)) {
            TextView userMessage = new TextView(this);
            userMessage.setBackgroundResource(R.drawable.user_message);

            String inputText = mInputMessage.getText().toString();
            userMessage.setText(inputText);
            LinearLayout.LayoutParams userMessageLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            userMessageLayout.gravity = Gravity.END;
            final int marginSize = getResources().getDimensionPixelSize(R.dimen.message_margin);
            userMessageLayout.setMargins(0,marginSize,0,marginSize);
            mMessageLog.addView(userMessage, userMessageLayout);
            mInputMessage.setText("");

            TextView cpuMessage = new TextView(this);
            cpuMessage.setBackgroundResource(R.drawable.cpu_message);
            LinearLayout.LayoutParams cpuMessageLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cpuMessageLayout.gravity = Gravity.START;
            cpuMessageLayout.setMargins(marginSize, marginSize, marginSize, marginSize);
            mMessageLog.addView(cpuMessage, cpuMessageLayout);
            cpuMessage.setText("OK");
        }
        */
    }

    class HTTPRequest extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL("http://221.186.150.142:10080/cgi-bin/honey.py?time=20151030000000");
                //url = new URL("http://www.google.co.jp");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                //return Integer.toString(connection.getResponseCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (true){
                    String line = reader.readLine();
                    if ( line == null ){
                        break;
                    }
                    return line;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String response) {
            ChatActivity.this.web.setText(response);
        }
    }
}
