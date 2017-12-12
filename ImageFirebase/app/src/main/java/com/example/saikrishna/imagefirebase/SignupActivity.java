package com.example.saikrishna.imagefirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText Firstname,Lastname,Email,ChoosePassword,RepeatPassword;
    Button Cancel,SignupButton;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Firstname.getText().toString().equals("") || Lastname.getText().toString().equals(" ") || Email.getText().toString().equals("") || ChoosePassword.getText().toString().equals("")|| RepeatPassword.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "Please enter the fields", Toast.LENGTH_SHORT).show();
                }
                if(ChoosePassword.getText().toString().trim().length()<6){
                    Toast.makeText(SignupActivity.this, "password must contain 6 or more characters", Toast.LENGTH_SHORT).show();

                }
                if(!ChoosePassword.getText().toString().equals(RepeatPassword.getText().toString())){
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }

}
