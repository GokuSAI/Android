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

public class MainActivity extends AppCompatActivity implements  LoginTask.LinkData {
    EditText Email,Password;
    Button Login,Signup;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


        Email = (EditText)findViewById(R.id.LoginEmailEdittext);
        Password=(EditText)findViewById(R.id.PasswordEdittext);
        Login=(Button)findViewById(R.id.LoginButton);
        Signup = (Button)findViewById(R.id.LoginSignupButton);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Email.getText().toString().equals("") || Password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please enter the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    new LoginTask(MainActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/login",Email.getText().toString(),Password.getText().toString());

                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Signupactivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    public void printLoggedUserDetails(User user) {
        Log.d("Enterers","OK");
        if(user == null || user.getStatus()== null ||user.getStatus().equals("error"))
            Toast.makeText(this, "INcorrect Details", Toast.LENGTH_SHORT).show();
        else
         {

           // Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            Log.d("UserDerails", user.toString());
            editor.putString("token", user.getToken());
            editor.putString("user_id",user.getUserId());
            editor.putString("FullName", user.getUserFname() + " " + user.getUserLname());
            editor.commit();
            Log.d("TOkenCSRF", pref.getString("token", "abcdef"));
            Log.d("LOgin","OK");
          Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
