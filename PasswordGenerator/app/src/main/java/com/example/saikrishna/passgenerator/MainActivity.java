package com.example.saikrishna.passgenerator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.*;
import android.os.Handler;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    SeekBar s1, s2, s3, s4;
    TextView c1, c2, c3, c4;
    Button generate;
    int x;
    ArrayList<String> password;
    ArrayList<String> passwords;
    static String pwd1 ="PASSWORD";
    static String pwd2 = "PASSWORDS";
    int ThreadCount;
    int ThreadLength;
    int AsyncCount;
    int AsyncLength;
    Executor threadPool;
    Handler handler;
    ProgressDialog progressDialog;
    int totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwords = new ArrayList<String>();
        password = new ArrayList<String>();
        final Intent intent = new Intent(MainActivity.this,GeneratedPasswords.class);

        c1 = (TextView) findViewById(R.id.Count1);
        c2 = (TextView) findViewById(R.id.Count2);
        c3 = (TextView) findViewById(R.id.Count3);
        c4 = (TextView) findViewById(R.id.Count4);
        s1 = (SeekBar) findViewById(R.id.seekBar);
        s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                c1.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        s2 = (SeekBar) findViewById(R.id.seekBar2);
        s2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int correction = 7;
            int val;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                val = progress + correction;
                c2.setText(val + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        s3 = (SeekBar) findViewById(R.id.seekBar3);
        s3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                c3.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        s4 = (SeekBar) findViewById(R.id.seekBar4);
        s4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                c4.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
            handler = new Handler(new Handler.Callback(){
                public boolean handleMessage(Message msg){
                    switch ( msg.what) {
                        case ThreadPass.STATUS_START:

                            //progressDialog.setProgress(0);
                            break;

                        case ThreadPass.STATUS_STEP:
                            progressDialog.setProgress(msg.getData().getInt("PROGRESS"));
                            break;
                        case ThreadPass.STATUS_DONE:
                            passwords=msg.getData().getStringArrayList("PASSWORDS");
                            //progressDialog.dismiss();
                            int i=0;
                            for(String pass:passwords){
                                Log.d("demo",passwords.get(i++).toString()) ;
                            }
                            break;
                    }
                    return false;
                }
            });


        threadPool = Executors.newFixedThreadPool(2);


        findViewById(R.id.Generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEMO","CLICKED");
                Toast.makeText(MainActivity.this, "dsfdf", Toast.LENGTH_SHORT).show();
               /* progressDialog.setMessage("Generating Passwords");
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                Log.d("DEMO","CLICKED");
                ThreadCount = Integer.parseInt(c1.getText().toString());
                ThreadLength = Integer.parseInt(c2.getText().toString());
                AsyncCount = Integer.parseInt(c3.getText().toString());
                AsyncLength = Integer.parseInt(c4.getText().toString());
                totalCount = ThreadCount + AsyncCount;
                threadPool.execute(new ThreadPass(ThreadCount, ThreadLength, totalCount));
                Log.d("demo","button clicked and thread created");
                AsyncTP Passgene = new AsyncTP(AsyncCount, AsyncLength,totalCount);
                Log.d("DEMMM","ASync started");
                Passgene.execute();*/
            }
        });
    }

    class ThreadPass implements Runnable {
        int count, length, total;

        public ThreadPass(int count, int length, int total) {
            this.count = count;
            this.length = length;
            this.total = total;
        }

        static final int STATUS_START = 0x00;
        static final int STATUS_STEP = 0x01;
        static final int STATUS_DONE = 0x02;


        @Override
        public void run() {

            Message msg = new Message();
            msg.what = STATUS_START;
            handler.sendMessage(msg);
            Util util = new Util();
            for (int i = 1; i <= count; i++) {

                String s = util.getPassword(length);

                passwords.add(s);
                msg = new Message();
                msg.what = STATUS_STEP;
                Bundle progress = new Bundle();
                 x += (100 * i)/total;
                progress.putInt("PROGRESS",x);
                msg.setData(progress);
                handler.sendMessage(msg);
            }
            msg= new Message();
            msg.what = STATUS_DONE;
            Bundle allPasswords = new Bundle();
            allPasswords.putStringArrayList("Passwords",passwords);
            msg.setData(allPasswords);
            Log.d("DEMO","data send");
            handler.sendMessage(msg);


        }
    }

class AsyncTP extends AsyncTask<Void , Integer , Void>{
    int count, length,totallength;
    AsyncTP(int count,int length,int totallength){
        this.count = count;
        this.length = length;
        this.totallength = totallength;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 1; i <= count; i++) {
            String s = Util.getPassword(length);
            password.add(s);
            x += (100 * (i)) / totallength;

            publishProgress(x);

        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        Intent i = new Intent(MainActivity.this,GeneratedPasswords.class);
        i.putExtra(MainActivity.pwd1,password);
        i.putExtra(MainActivity.pwd2,passwords);
        startActivity(i);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);

    }

        }
}