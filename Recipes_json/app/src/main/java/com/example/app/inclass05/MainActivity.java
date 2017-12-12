package com.example.app.inclass05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements  ImageView.OnClickListener{
    ImageView add,remove;
    EditText e1;
    LinearLayout l,h;
    Button search;
    public static ArrayList<String> A = new ArrayList();
    public static EditText dish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dish = (EditText)findViewById(R.id.DishText);
        search = (Button) findViewById(R.id.SearchButton);
        l = (LinearLayout) findViewById(R.id.LinearLayout);
        //pd=(ProgressBar)findViewById(R.id.progressBar);

        h = new LinearLayout(this);
        e1 = new EditText(this);
        e1.setWidth(950);

        add = new ImageView(this);
        add.setImageResource(R.drawable.add);
        add.setTag("add");
        //i = add.getId();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
        add.setLayoutParams(lp);
        h.addView(e1);
        h.addView(add);
        l.addView(h);
        add.setOnClickListener(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.clear();

                for (int i = 0; i < l.getChildCount(); i++) {
                    LinearLayout j = (LinearLayout) l.getChildAt(i);
                    EditText e2 = (EditText) j.getChildAt(0);
                    String s = e2.getText().toString();
                    if (s.isEmpty() || s.equals(" ")) {

                        //validdetails = false;
                    } else {
                        A.add(s);
                        //validdetails=true;
                    }


                }
                if(A.size()==0 || dish.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                    startActivity(intent);
                    Log.d("DEMO", A.toString());
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        ImageView test = (ImageView) v;



            if (test.getTag().equals("add")) {
                if (l.getChildCount() < 5) {

                    test.setImageResource(R.drawable.remove);
                    test.setTag("remove");
                    l = (LinearLayout) findViewById(R.id.LinearLayout);


                    h = new LinearLayout(this);
                    e1 = new EditText(this);
                    e1.setWidth(950);

                    ImageView add1 = new ImageView(this);
                    add1.setImageResource(R.drawable.add);
                    add1.setTag("add");
                    // i = add.getId();
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
                    add1.setLayoutParams(lp);
                    h.addView(e1);
                    h.addView(add1);
                    l.addView(h);
                    add1.setOnClickListener(this);
                }
            }else {
                LinearLayout temp = (LinearLayout) test.getParent();
                temp.removeView(temp.getChildAt(0));
                //temp.removeView(temp.getChildAt(1));
                l.removeView(temp);


            }
        }

    }



