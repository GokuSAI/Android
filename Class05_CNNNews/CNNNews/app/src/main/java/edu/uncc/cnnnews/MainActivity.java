package edu.uncc.cnnnews;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
a. Assignment #. In class assignment 05
b. File Name. InClass05_Mylavarapu.zip
c. Full name of all students in your group. Sai Yesaswy Mylavarapu
*/

public class MainActivity extends AppCompatActivity implements GetImageAsyncTask.IImage,GetNewsAsyncTask.Idata {

    final static String url = "http://rss.cnn.com/rss/cnn_tech.rss";
    ArrayList<CNNNews> news;
    int counter=0,size;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getNews = (Button)findViewById(R.id.getNews);

        final ImageButton first = (ImageButton)findViewById(R.id.btnFirst);
        final ImageButton prev = (ImageButton)findViewById(R.id.btnPrevious);
        final ImageButton last = (ImageButton)findViewById(R.id.btnLast);
        final ImageButton next = (ImageButton)findViewById(R.id.btnNext);

        first.setEnabled(false);
        prev.setEnabled(false);
        next.setEnabled(false);
        last.setEnabled(false);

        getNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                first.setEnabled(true);
                prev.setEnabled(true);
                next.setEnabled(true);
                last.setEnabled(true);
                progress = new ProgressDialog(MainActivity.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();
                new GetNewsAsyncTask(MainActivity.this).execute(url);
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    counter=0;
                    FillNewsDetails(news.get(counter));
                }

            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null) {
                    counter = size - 1;
                    FillNewsDetails(news.get(counter));
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    if(counter>0) {
                        counter -= 1;
                        FillNewsDetails(news.get(counter));
                    }
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news!=null){
                    if(counter<size-1) {
                        counter += 1;
                        FillNewsDetails(news.get(counter));
                    }
                }
            }
        });
        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void FillNewsDetails(CNNNews news1){
        if(news1!=null) {
            new GetImageAsyncTask(MainActivity.this).execute(news1.getUrltoimage());

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.newsView);
            //spinner.setBackground(getResources().getDrawable(R.drawable.spinner_border));

            linearLayout.removeAllViews();

            TextView textView = new TextView(MainActivity.this);
            textView.setText(news1.getTitle());
            textView.setTextSize(15);
            textView.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            String dateString = news1.getPubdate();
            SimpleDateFormat fmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
            Date date = null;
            try {
                date = fmt.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd");
            String datefinal = fmtOut.format(date);
            textView.setText("Published on: " + datefinal);
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText("Description:");
            linearLayout.addView(textView);

            textView = new TextView(MainActivity.this);
            textView.setText(news1.getDescription());
            linearLayout.addView(textView);
        }
    }

    @Override
    public void setupImage(Bitmap result) {
        if(result!=null){
            ImageView newsImage = (ImageView)findViewById(R.id.newsImage);
            newsImage.setImageBitmap(result);
            progress.dismiss();
        }
    }

    @Override
    public void setupData(ArrayList<CNNNews> result) {
        if(result!=null) {
            news = result;
            size = result.size();
            FillNewsDetails(news.get(counter));
        }
        else {
            Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
}
