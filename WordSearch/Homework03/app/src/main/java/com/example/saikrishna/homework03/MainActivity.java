package com.example.saikrishna.homework03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements  ImageView.OnClickListener{
LinearLayout l,h;
    Button search;
    CheckBox ch;
    public ArrayList<String> A;
    ImageView add;
    EditText e1;
    ProgressBar pd
            ;
    int i,number,count=0;
    boolean validdetails = true;
    boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressBar(this);
        search=(Button)findViewById(R.id.button);
        ch =(CheckBox)findViewById(R.id.checkBox);
        //result = new ArrayList<>();
        A = new ArrayList<String>();
        l= (LinearLayout)findViewById(R.id.LinearLayout);
        pd=(ProgressBar)findViewById(R.id.progressBar);

         h =new LinearLayout(this);
         e1 =new EditText(this);
        e1.setWidth(950);

        add = new ImageView(this);
        add.setImageResource(R.drawable.add);
        add.setTag("add");
        //i = add.getId();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100);
        add.setLayoutParams(lp);
        h.addView(e1);
        h.addView(add);
        l.addView(h);
        add.setOnClickListener(this);

        String line,line2;
        InputStream is1=null;
        BufferedReader br1=null;

        try {
            is1 = getAssets().open("textfile.txt");
            br1 = new BufferedReader(new InputStreamReader(is1));
            while((line = br1.readLine())!= null){
                number++;
            }

            pd.setMax(number);

        }

        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.clear();

                for(int i =0; i<l.getChildCount();i++) {
                    LinearLayout j = (LinearLayout) l.getChildAt(i);
                    EditText e2 = (EditText) j.getChildAt(0);
                    String s = e2.getText().toString();
                    if(!s.equals(" ")){
                        A.add(s);
                        validdetails=true;
					}
                }
                if(A.size()==0){
                    Toast.makeText(getApplicationContext(),"Input cannot be empty",Toast.LENGTH_SHORT);
                    validdetails = false;
                }

if(validdetails){
   pd.setVisibility(View.VISIBLE);
if(ch.isChecked()){
    new Async().execute(1);
}
else{
    new Async().execute(0);
}

}



            }
        });
    }
                class Async extends AsyncTask< Integer, Integer , ArrayList<String>>{
                    InputStream is;
                    BufferedReader br;
                    String line,line2,linetemp,linetemp2;
                    ArrayList<String> result;

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        //super.onProgressUpdate(values);

                        pd.setProgress(values[0]);
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected ArrayList<String> doInBackground(Integer... params) {

                        int match_case = params[0];

                        try{
                            result = new ArrayList<>();
                            is = getAssets().open("textfile.txt");
                            br = new BufferedReader(new InputStreamReader(is));
                            int counter =0;
                            boolean line2Used = true;


                            while(true){



                                if(line2Used) {


                                    line = br.readLine();
                                    if (line == null)
                                        break;

                                    counter++;


                                    line = line.replaceAll("\\W", " ");

                                    while (line.length() <= 30) {
                                        String lineTemp = br.readLine();
                                        counter++;
                                        if (lineTemp == null)
                                            break;

                                        line = line + lineTemp;
                                        line = line.replaceAll("\\W", " ");

                                    }
                                }
                                line2 = "";
                                while(line2.length() <= 30 ) {
                                    String lineTemp =  br.readLine();
                                    counter++;
                                    if(lineTemp == null)
                                        break;
                                    line2 = line2  + lineTemp ;
                                    line2 = line2.replaceAll("\\W", " ");

                                }
                                if(line2.length() < 30)
                                {
                                    break;
                                }

                                // f2.write(line);
                                //f2.write("\n");
                                // System.out.println(line);

                                //String[] words = line.split(" ");




                                            for (int i = 0; i < A.size(); i++) {
                                                if(match_case == 1) {
                                                    if (line.contains(A.get(i))) {

                                                        count++;
                                                        int idx = line.indexOf(A.get(i));
                                                        if ((idx - 10 > 0) && ((idx + 20) < (line.length()))) {
                                                            String sub = line.substring(idx - 10, idx + 20);

                                                            result.add("..." + sub + "...");
                                                            line2Used = false;

                                                        } else if (idx - 10 < 0) {count++;

                                                            int t = 10 - idx;
                                                            String sub = "";

                                                            sub =  line.substring(0, idx + 20 + t);


                                                            result.add("..." + sub + "...");
                                                            line2Used = false;

                                                        } else if (idx + 20 > (line.length())) {count++;
                                                            int t = 20 + idx - line.length();
                                                            String sub = "";
                                                            sub = line.substring(idx - 10, line.length());
                                                            if (line2 != null) {
                                                                if (line2.length() > t) {
                                                                    sub = sub + line2.substring(0, t);
                                                                } else {
                                                                    sub = sub + line2.substring(0, line2.length() - 1);

                                                                }
                                                            }

                                                            result.add("..." + sub + "...");
                                                            line2Used = true;


                                                        }


                                                    }
                                                }
                                                else{
                                                    String smallline = line.toLowerCase();
                                                    String smallsearch = A.get(i).toLowerCase();

                                                        if (smallline.contains(smallsearch)) {
                                                            String tempLine,tempLine2,wordTemp;

                                                            tempLine = line;
                                                            tempLine2 = line2;
                                                            wordTemp = A.get(i);

                                                            line = line.toLowerCase();
                                                            line2 = line2.toLowerCase();
                                                            //smallsearch = smallsearch.toLowerCase();

                                                            int idx = line.indexOf(smallsearch);
                                                            if ((idx - 10 > 0) && ((idx + 20) < (line.length()))) {count++;
                                                                String sub = tempLine.substring(idx - 10, idx + 20);

                                                                result.add("..." + sub + "...");
                                                                line2Used = false;
                                                            } else if (idx - 10 < 0) { count++;

                                                                int t = 10 - idx;
                                                                String sub = "";

                                                                sub = tempLine.substring(0, idx + 20 + t);

                                                                result.add("..." + sub + "...");
                                                                line2Used = false;
                                                            } else if (idx + 20 > (line.length())) {count++;
                                                                int t = 20 + idx - line.length();
                                                                String sub = "";
                                                                sub = tempLine.substring(idx - 10, line.length());
                                                                if (line2 != null) {
                                                                    if (line2.length() > t) {
                                                                        sub = sub + tempLine2.substring(0, t);
                                                                    } else {
                                                                        sub = sub + tempLine2.substring(0, tempLine2.length() - 1);

                                                                    }
                                                                }

                                                                result.add("..." + sub + "...");
                                                                line2Used = true;

                                                            }


                                                        }

                                                }

                                           // }






                                }
                            publishProgress(counter);

                         //   onProgressUpdate(new Integer(counter));

                                if(!line2Used){
                                    line = line2;

                                }

                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        finally{
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        return result;
                    }



                    @Override
                    protected void onPostExecute(ArrayList<String> strings) {
                       // super.onPostExecute(strings);
                        //pd.dismiss();
                        pd.setVisibility(View.INVISIBLE);
                        if(strings.isEmpty()){
                            Toast.makeText(MainActivity.this, "No words found", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d("demostring", strings.toString());
                            String temp = String.valueOf(count);
                            Log.d("TOTAL COUNT :",temp);
                            boolean c =ch.isChecked();
                            Intent intent = new Intent(MainActivity.this, Results.class);
                            intent.putExtra("RESULT_KEY", strings);
                            intent.putExtra("WORDS_KEY",A);
                            intent.putExtra("CHECKBOX",c);
                            startActivity(intent);
                        }

                        super.onPostExecute(strings);
                    }


                }

    @Override
    public void onClick(View v) {
        ImageView test = (ImageView)v;

        if( test.getTag().equals("add")){
            if(l.getChildCount() <20){

            test.setImageResource(R.drawable.remove);
            test.setTag("remove");
            l= (LinearLayout)findViewById(R.id.LinearLayout);


            h =new LinearLayout(this);
            e1 =new EditText(this);
            e1.setWidth(950);

            ImageView add1 = new ImageView(this);
            add1.setImageResource(R.drawable.add);
            add1.setTag("add");
           // i = add.getId();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100);
            add1.setLayoutParams(lp);
            h.addView(e1);
            h.addView(add1);
            l.addView(h);
            add1.setOnClickListener(this);}
        }
        else{
            LinearLayout temp = (LinearLayout)test.getParent();
            temp.removeView(temp.getChildAt(0));
            //temp.removeView(temp.getChildAt(1));
            l.removeView(temp);


        }

    }

}


