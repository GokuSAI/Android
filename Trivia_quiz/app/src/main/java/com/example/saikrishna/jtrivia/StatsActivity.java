package com.example.saikrishna.jtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    Button quit,tryagain;
    TextView percent;
    TextView congrats;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        congrats = (TextView)findViewById(R.id.textView3);
        congrats.setVisibility(View.INVISIBLE);
        percent = (TextView) findViewById(R.id.percent);
        quit = (Button) findViewById(R.id.quit1);
        tryagain = (Button) findViewById(R.id.tryagain);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        int percentage = getIntent().getExtras().getInt(TriviaActivity.PERCENT_KEY);
        progressBar.setProgress(percentage);
        percent.setText(percentage+"%");
        final ArrayList<Questions> quest ;
        quest = getIntent().getParcelableArrayListExtra("QUESTION");
        if(percentage==100){
            findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
            congrats.setVisibility(View.VISIBLE);

        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StatsActivity.this,MainActivity.class);
                startActivity(in);
            }
        });
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StatsActivity.this,TriviaActivity.class);
                in.putParcelableArrayListExtra("QUESTION",quest);
                startActivity(in);
            }
        });
    }
}
