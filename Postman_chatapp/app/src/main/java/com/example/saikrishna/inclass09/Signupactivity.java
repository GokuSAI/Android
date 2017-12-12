package com.example.saikrishna.inclass09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.saikrishna.inclass09.MainActivity.MY_PREFS_NAME;

public class Signupactivity extends AppCompatActivity implements SignupTask.LinkSignedInData {

EditText Firstname,Lastname,Email,ChoosePassword,RepeatPassword;
    Button Cancel,SignupButton;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        Firstname = (EditText)findViewById(R.id.FirstNameEdittext);
        Lastname=(EditText)findViewById(R.id.LastNameEdittext);
        Email = (EditText)findViewById(R.id.EmailEdittext);
        ChoosePassword=(EditText)findViewById(R.id.ChoosePassEdittext);
        RepeatPassword=(EditText)findViewById(R.id.RepeatPassEdittext);

        Cancel=(Button)findViewById(R.id.CancelButton);
        SignupButton=(Button)findViewById(R.id.SignupButton);


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signupactivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Firstname.getText().toString().equals("") || Lastname.getText().toString().equals(" ") || Email.getText().toString().equals("") || ChoosePassword.getText().toString().equals("")|| RepeatPassword.getText().toString().equals("")){
                    Toast.makeText(Signupactivity.this, "Please enter the fields", Toast.LENGTH_SHORT).show();
                }
                if(ChoosePassword.getText().toString().trim().length()<6){
                    Toast.makeText(Signupactivity.this, "password must contain 6 or more characters", Toast.LENGTH_SHORT).show();

                }
                if(!ChoosePassword.getText().toString().equals(RepeatPassword.getText().toString())){
                    Toast.makeText(Signupactivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    new SignupTask(Signupactivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/signup",Email.getText().toString(),ChoosePassword.getText().toString(),Firstname.getText().toString(),Lastname.getText().toString());

                }
            }
        });
    }

    @Override
    public void printSignedInUserDetails(User user) {
        if(user.getStatus().equals("error"))
            Toast.makeText(this, "User already Exists", Toast.LENGTH_SHORT).show();
        else {
            //Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("token", user.getToken());
            editor.putString("user_id",user.getUserId());
            editor.putString("FullName", user.getUserFname() + " " + user.getUserLname());
            editor.commit();
            Intent intent=new Intent(Signupactivity.this,ThreadActivity.class);
            startActivity(intent);
            finish();
        }
        //Log.d("TOkenCSRF",pref.getString("token","abcdef"));
        //Log.d("Signup","OK");


    }
}
