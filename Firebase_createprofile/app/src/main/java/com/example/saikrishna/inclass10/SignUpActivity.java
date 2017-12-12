package com.example.saikrishna.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText SFname,SLname,SEmail,SPassword,SCPassword;
    Button Signup2,Cancel;
    User user;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        childref = dataref.child("notes");
        mAuth = FirebaseAuth.getInstance();
        SFname = (EditText)findViewById(R.id.FirstNameSignup);
        SLname=(EditText)findViewById(R.id.LastNameSignup);
        SEmail=(EditText)findViewById(R.id.EmailSignup);
        SPassword=(EditText)findViewById(R.id.PasswordSignup);
        SCPassword=(EditText)findViewById(R.id.ConfirmPassSignup);
        final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Signup2=(Button)findViewById(R.id.Signup2Button);
        Cancel=(Button)findViewById(R.id.CancelButton);

        String email =SEmail.getText().toString();
        final Matcher m = emailPattern.matcher(email);

        Signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SFname.getText().toString().equals("")||SLname.getText().toString().equals("")||SEmail.getText().toString().equals("")||SPassword.getText().toString().equals("")||SCPassword.getText().toString().equals("") ){
                    Toast.makeText(SignUpActivity.this, "Enter all the Fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    user = new User();
                    user.setfirstname(SFname.getText().toString());
                    user.setlastname(SLname.getText().toString());
                    user.setemail(SEmail.getText().toString());
                    user.setpassword(SPassword.getText().toString());

                    //Log.d("Created",user.toString());
                    DatabaseReference newRef = childref.push();
                    user.setid(String.valueOf(newRef));

                    newRef.setValue(user);

                    mAuth.createUserWithEmailAndPassword(SEmail.getText().toString(), SPassword.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Intent intent = new Intent(SignUpActivity.this, ContactsList.class);
                                        startActivity(intent);
                                        finish();
                                        //updateUI(user);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                       // updateUI(null);
                                    }

                                    // ...
                                }
                            });

                    //mAuth.createUserWithEmailAndPassword(SEmail.getText().toString(), SPassword.getText().toString());

                    //Intent intent = new Intent(SignUpActivity.this, ContactsList.class);
                    //startActivity(intent);
                    //finish();
                    //FillAdapter(notesList);
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
