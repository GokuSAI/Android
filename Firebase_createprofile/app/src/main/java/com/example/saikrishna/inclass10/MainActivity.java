package com.example.saikrishna.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button Login,Signup;
    User user;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    private FirebaseAuth mAuth;
    static String user_data = "DATA";
    static String LIST = "LIST";
    public static ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        childref = dataref.child("notes");
        Signup=(Button)findViewById(R.id.SignUpButton);
        Login=(Button)findViewById(R.id.LoginButton);

        email=(EditText)findViewById(R.id.EmailEditText);
        password=(EditText)findViewById(R.id.PasswordEditText);

        mAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equals("")||email.getText().toString()== null|| password.getText().toString().equals("")||password.getText().toString()==null){
                    Toast.makeText(MainActivity.this, "please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("dssds", "signInWithEmail:onComplete:" + task.isSuccessful());
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, ContactsList.class);
                                        intent.putExtra(LIST, user);
                                        startActivity(intent);
                                        finish();
                                    }


                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w("demo", "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
