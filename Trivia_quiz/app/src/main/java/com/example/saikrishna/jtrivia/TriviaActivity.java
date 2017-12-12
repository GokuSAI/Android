package com.example.saikrishna.jtrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImage.ImageUI {
    ArrayList<String> userAnswer = new ArrayList();
    ArrayList<Questions> ques = new ArrayList();
    public static String PERCENT_KEY = "PERCENT";
    int i=0,selectedId=-1;
    RadioGroup rg;
    TextView que,question,loadingImg,seconds;
    Button quit, next;
    ProgressBar progress;
    RelativeLayout relativeLayout;
    ImageView img;
    int count = 0;
    int percentCorrect =0;
    CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        que = (TextView)findViewById(R.id.ques);
        question = (TextView) findViewById(R.id.question);
        quit = (Button) findViewById(R.id.quit);
        next = (Button) findViewById(R.id.next);
        progress = (ProgressBar)findViewById(R.id.progressBar2);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        img = (ImageView) findViewById(R.id.imageView2);
        loadingImg = (TextView) findViewById(R.id.loadingImg);
        ques = getIntent().getParcelableArrayListExtra("QUESTION");
        seconds = (TextView) findViewById(R.id.seconds);

        //final CountDownTimer finalTimer = timer;

        if(!ques.isEmpty()){
            //ques = getIntent().getExtras().getParcelableArrayList("QUESTION");
            timer = new CountDownTimer(120000, 1000) {

                public void onTick(long millisUntilFinished) {
                    seconds.setText(millisUntilFinished / 1000 + " seconds");
                }

                public void onFinish() {
                    selectedId = rg.getCheckedRadioButtonId();
                    userAnswer.add(selectedId+"");

                    for(int j=0;j<userAnswer.size();j++){

                        if(userAnswer.get(j).equals(ques.get(j).getAnswer()))
                            count++;
                    }
                    percentCorrect = ((count*100)/ques.size());
                    Intent in = new Intent(TriviaActivity.this,StatsActivity.class);
                    in.putExtra(PERCENT_KEY,percentCorrect);
                    in.putExtra("QUESTION",ques);
                    startActivity(in);
                }
            }.start();

            new GetImage(this).execute(ques.get(i).getImage());
            question.setText("Q"+(i+1));
            que.setText(ques.get(i).getText());
            try {
                createRadioButton(ques.get(i).getOptions().size(),i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                Intent in = new Intent(TriviaActivity.this,MainActivity.class);
                startActivity(in);


            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                selectedId = rg.getCheckedRadioButtonId();
                userAnswer.add(selectedId+"");
                if(i<ques.size()){
                    relativeLayout.removeAllViewsInLayout();
                    progress.setVisibility(View.VISIBLE);
                    loadingImg.setVisibility(View.VISIBLE);
                    img.setVisibility(View.INVISIBLE);
                    question.setText("Q"+(i+1));
                    que.setText(ques.get(i).getText());
                    try {
                        createRadioButton(ques.get(i).getOptions().size(),i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new GetImage(TriviaActivity.this).execute(ques.get(i).getImage());
                }
                else{
                    timer.cancel();
                    for(int j=0;j<ques.size();j++){
                        if(userAnswer.get(j).equals(ques.get(j).getAnswer()))
                            count++;
                    }
                    percentCorrect = ((count*100)/ques.size());
                    Intent in = new Intent(TriviaActivity.this,StatsActivity.class);
                    in.putExtra(PERCENT_KEY,percentCorrect);
                    in.putExtra("QUESTION",ques);
                    startActivity(in);
                }
            }
        });

    }

    private void createRadioButton(int size, int index) throws JSONException {
        final RadioButton[] rb = new RadioButton[size];
        rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        for(int i=0; i<size; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(ques.get(index).getOptions().get(i));
            rb[i].setId(i+1);
            rg.addView(rb[i]);
        }
        relativeLayout.addView(rg);//you add the whole RadioGroup to the layout

    }


    @Override
    public void setImageUI(Bitmap image) {
        img.setImageBitmap(image);
        img.setVisibility(View.VISIBLE);
        progress.setVisibility(View.INVISIBLE);
        loadingImg.setVisibility(View.INVISIBLE);

    }


    private boolean isNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
            return true;
        return false;
    }

}
