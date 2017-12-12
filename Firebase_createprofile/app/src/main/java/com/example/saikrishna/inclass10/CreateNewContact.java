package com.example.saikrishna.inclass10;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateNewContact extends AppCompatActivity {

    RadioGroup rg;
    EditText name,email,phone;
    Button submit;
    ImageView imgg;
    User newuser;

    String img = "";


    ArrayList<User> AllUsers = new ArrayList<>();
    public static final String VALUE_KEY = "IMG";
    public static final int REQ_CODE =100;

    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
        childref = dataref.child("notes");
       // AllUsers = new ArrayList<>();
        //AllUsers = (ArrayList<User>) getIntent().getSerializableExtra(MainActivity.LIST);
        name = (EditText)findViewById(R.id.CreateNameEdittext);
        email=(EditText)findViewById(R.id.CreateEmailEdittext);
        phone =(EditText)findViewById(R.id.CreatePhoneEditText);
        imgg=(ImageView)findViewById(R.id.dp);
        submit = (Button)findViewById(R.id.SubmitButton);
        //logout=(Button)findViewById(R.id.LogoutButton);
        final String[] radiovalue = {new String()};
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //RadioButton rb = (RadioButton)findViewById(checkedId);
                radiovalue[0] = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

            }
        });


        findViewById(R.id.dp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewContact.this, SelectAvatar.class);
                startActivityForResult(intent,REQ_CODE);



            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AllUsers = new ArrayList<>();
                newuser = new User();
                newuser.setfirstname(name.getText().toString());

                newuser.setemail(email.getText().toString());
                newuser.setphone(phone.getText().toString());
                newuser.setdept(radiovalue[0]);
                newuser.setimg(img);
                Log.d("NEW USER",newuser.toString());

                AllUsers.add(newuser);

                Log.d("ALL users",AllUsers.toString());
//

                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String ky = dataref.child("contacts").child(id).push().getKey();
                dataref.child("contacts").child(id).child(ky).setValue(newuser);

                Log.d("USERID",id);
                String idnote = id.substring(id.indexOf("Notes/-")+6).trim();
                Log.d("USERID",idnote);

//
//



                Intent intent = new Intent(CreateNewContact.this, ContactsList.class);
                intent.putExtra(MainActivity.user_data,AllUsers);
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView image = (ImageView) findViewById(R.id.dp);
        // Check which request we're responding to
        if (requestCode == REQ_CODE) {

            if(data.getExtras().getString(VALUE_KEY).equals("img1")){
                image.setImageResource(R.drawable.avatar_f_1);
                img = "img1";
            }
            else if(data.getExtras().getString(VALUE_KEY).equals("img2")){
                image.setImageResource(R.drawable.avatar_m_3);
                img = "img2";
            }
            else if(data.getExtras().getString(VALUE_KEY).equals("img3")){
                image.setImageResource(R.drawable.avatar_f_2);
                img = "img3";
            }
            else if(data.getExtras().getString(VALUE_KEY).equals("img4")){
                image.setImageResource(R.drawable.avatar_m_2);
                img = "F4";

            }
            else if(data.getExtras().getString(VALUE_KEY).equals("img5")){
                image.setImageResource(R.drawable.avatar_f_3);
                img = "F5";
            }
            else if(data.getExtras().getString(VALUE_KEY).equals("img6")){
                image.setImageResource(R.drawable.avatar_m_1);
                img = "F6";
            }
        }
    }



}
