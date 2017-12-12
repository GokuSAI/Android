package com.example.saikrishna.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
int num;
    public List<User> UserList;

    TextView Dname,Demail,Ddept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        if(getIntent().getExtras()!=null){
            num =  getIntent().getExtras().getInt("SENT");
            Log.d("INtente",num+"");
        }

        Dname =(TextView)findViewById(R.id.NAME);
        Demail=(TextView)findViewById(R.id.email);
        Ddept=(TextView)findViewById(R.id.Dept);

       User user= ContactsList.p.get(num);

        Dname.setText(user.getfirstname());
        Demail.setText(user.getemail());
        Ddept.setText(user.getdept());

    }
}
