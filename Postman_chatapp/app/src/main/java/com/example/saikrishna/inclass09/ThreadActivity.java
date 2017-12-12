package com.example.saikrishna.inclass09;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreadActivity extends AppCompatActivity implements ThreadTask.LinkData,AddThreadTask.AddLinkData, CustomAdapter.deleteMy{
    CustomAdapter customAdapter;
    private ListView favlistview;
    TextView nameofuser;
    EditText threadedittext;
    ImageView threadimg,logoutimage;
    String threadentered;
    static String position_key = "keytst";
    static String threadname;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences pref;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Threadclass> logged = new ArrayList<>();
    ArrayList<Threadclass> logged1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Log.d("pref",pref.getString("token",""));
        favlistview = (ListView) findViewById(R.id.Thread_list_view);
        logoutimage=(ImageView)findViewById(R.id.imageView);
        logoutimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = ThreadActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sh.edit();
                ed.clear();
                ed.commit();
                Intent intent = new Intent(ThreadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        favlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(Customlayout.this, "Great", Toast.LENGTH_SHORT).show();


                Threadclass x = null;
                if(logged1.size() > 0) {

                    x = logged1.get(position);
                }
                else{
                    x = logged.get(position);
                }

                x.getID();
                threadname =x.getTitle();
                Intent intent = new Intent(ThreadActivity.this,ChatroomActivity.class);
//                Log.d("Sendgin",x.getID());
                intent.putExtra(position_key,x.getID());
                startActivity(intent);

            }
        });
        threadedittext = (EditText)findViewById(R.id.AddThreadExittext);
        nameofuser = (TextView)findViewById(R.id.ThreadTextView);
        threadimg=(ImageView)findViewById(R.id.AddThreadImageView);

        new ThreadTask(ThreadActivity.this,this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread",pref.getString("token",""));

        threadentered =threadedittext.getText().toString();
        threadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddThreadTask(ThreadActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/add",pref.getString("token",""),threadedittext.getText().toString());
                threadedittext.setText("");
            }
        });


    }

    @Override
    public void printLoggedUserDetails(ArrayList<Threadclass> th) {


            logged = th;
            customAdapter = new CustomAdapter(this, R.layout.customlayout, th);
            //Log.d("Title of thread",th.toString());

            favlistview.setAdapter(customAdapter);

            nameofuser.setText(pref.getString("FullName", ""));

    }


    @Override
    public void printAddedUserDetails(ArrayList<Threadclass> th) {



        if(th!=null) {
            th.addAll(logged);

            logged1=th;
            customAdapter = new CustomAdapter(this, R.layout.customlayout, th);
            //Log.d("Title",th.toString());

            favlistview.setAdapter(customAdapter);
        }
        nameofuser.setText(pref.getString("FullName",""));

    }

    @Override
    public void deleteThread(int pos) {
        new DeleteTask(ThreadActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/delete/"+pos,pref.getString("token",""));

    }

}
