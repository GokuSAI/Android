package com.example.saikrishna.homework03;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

public class Results extends AppCompatActivity {
ListView l;
    ArrayList<String> custom;
    ArrayList<String> colors;
    boolean c1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if(getIntent().getExtras()!= null){
             custom= (ArrayList<String>) getIntent().getExtras().get("RESULT_KEY");
            colors = (ArrayList<String>) getIntent().getExtras().get("WORDS_KEY");
            c1 = (boolean) getIntent().getExtras().get("CHECKBOX");
        }
        l = (ListView)findViewById(R.id.Listview);
        CustomAdapter customAdapter = new CustomAdapter();
        l.setAdapter(customAdapter);
		finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return custom.size();
        }

        @Override
        public String getItem(int position) {
            return (String)custom.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView e = (TextView) convertView.findViewById(R.id.textView12);


            e.setText(custom.get(position), TextView.BufferType.SPANNABLE);

            if(c1) {


                String line = custom.get(position);
                int i = 0;
                int start = 5, end = 8;
                for (i = 0; i < colors.size(); i++) {

                    if (line.contains(colors.get(i))) {
                        start = line.indexOf(colors.get(i));
                        end = start + colors.get(i).length();
                        break;

                    }
                }
                Spannable s = (Spannable) e.getText();
                ForegroundColorSpan fs = null;

                if( (i+1) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.GREEN);
                }

                else if (  (i + 2) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.RED);
                }
                else if ( (i + 3) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.BLUE);

                }


                s.setSpan(fs, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            else{
                String line = custom.get(position);
                int i = 0;
                ArrayList<String> temp ;
                temp = new ArrayList<String>();
                line = line.toLowerCase();
                 int start = 5, end = 8;
                //Log.d("DEMOLOOp","Entered into match case");
                for (i = 0; i < colors.size(); i++) {

                    temp.add(i,colors.get(i).toLowerCase());


                    if (line.contains(temp.get(i))) {
                        start = line.indexOf(temp.get(i));
                        end = start + temp.get(i).length();
                        break;

                    }
                }
                Spannable s = (Spannable) e.getText();
                ForegroundColorSpan fs = null;

                if( (i+1) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.GREEN);
                }

                else if (  (i + 2) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.RED);
                }
                else if ( (i + 3) % 3 == 0) {
                    fs = new ForegroundColorSpan(Color.BLUE);

                }


                s.setSpan(fs, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }





            return convertView;
        }
    }
}
