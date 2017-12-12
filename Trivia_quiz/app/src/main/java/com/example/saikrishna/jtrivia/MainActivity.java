package com.example.saikrishna.jtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadTriviaAsync.SettingUI {
    ArrayList<Questions> ques = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        findViewById(R.id.ready).setVisibility(View.INVISIBLE);
        findViewById(R.id.start).setEnabled(false);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
        if(isNetworkConnection()) {
            new LoadTriviaAsync(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");


        }else{
            Toast.makeText(getApplicationContext(),"No network connection",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void setUI(final ArrayList<Questions> question) {
        ques = question;
        findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        findViewById(R.id.ready).setVisibility(View.VISIBLE);
        findViewById(R.id.start).setEnabled(true);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
                intent.putParcelableArrayListExtra("QUESTION",question);
                startActivity(intent);

            }
        });
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
    }

    private boolean isNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
            return true;
        return false;
    }
}


