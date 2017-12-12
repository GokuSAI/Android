package com.example.saikrishna.gsignin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText SFname,SLname,SEmail,Sbirthday,SPassword,SCPassword;
    Button Signup2,Cancel;
    Profile user;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    private FirebaseAuth mAuth;
    DatePickerDialog mDatePickerDialog = null;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        childref = dataref.child("users");
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SFname = (EditText) findViewById(R.id.FirstNameSignup);
        SLname = (EditText) findViewById(R.id.LastNameSignup);
        SEmail = (EditText) findViewById(R.id.EmailSignup);
        Sbirthday = (EditText) findViewById(R.id.SignUpBirthday);
        SPassword = (EditText) findViewById(R.id.PasswordSignup);
        SCPassword = (EditText) findViewById(R.id.ConfirmPassSignup);
        final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Signup2 = (Button) findViewById(R.id.Signup2Button);
        Cancel = (Button) findViewById(R.id.CancelButton);

        String email = SEmail.getText().toString();
        final Matcher m = emailPattern.matcher(email);


                Sbirthday.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        if (mDatePickerDialog == null) {
                            mDatePickerDialog = new DatePickerDialog(SignUpActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            Sbirthday.setText(String.format(
                                                    "%04d-%02d-%02d", year, monthOfYear + 1,
                                                    dayOfMonth));
                                            myCalendar.set(Calendar.YEAR, year);
                                            myCalendar.set(Calendar.MONTH, monthOfYear);
                                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        }
                                    },2017,12,01);
                            myCalendar.set(Calendar.YEAR, 2003);
                            myCalendar.set(Calendar.MONTH, 12);
                            myCalendar.set(Calendar.DAY_OF_MONTH, 01);
                            mDatePickerDialog.setCanceledOnTouchOutside(true);
                            // mDatePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                            mDatePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                        }
                        mDatePickerDialog.show();

                /*new DatePickerDialog(CreateNewContact.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                    }
                });



        Signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Log.d("DATE",dateFormat.format(date));
                String[] d = dateFormat.format(date).split(" ");
                String[] dt = d[0].split("-");
                String[] dtOfBt = Sbirthday.getText().toString().split("-");
                if(Integer.parseInt(dt[0])<Integer.parseInt(dtOfBt[0])){
                    Toast.makeText(SignUpActivity.this,"Year cannot be greater than current year",Toast.LENGTH_LONG).show();
                }
                if((Integer.parseInt(dt[0])-Integer.parseInt(dtOfBt[0]))<13){
                    Toast.makeText(SignUpActivity.this,"Age should be greater than 13",Toast.LENGTH_LONG).show();
                }
                else if((Integer.parseInt(dt[0])-Integer.parseInt(dtOfBt[0]))==13){
                    if((Integer.parseInt(dt[1])-Integer.parseInt(dtOfBt[1]))<13){
                        Toast.makeText(SignUpActivity.this,"Age should be greater than 13",Toast.LENGTH_LONG).show();
                    }
                    else if((Integer.parseInt(dt[1])-Integer.parseInt(dtOfBt[1]))==13){
                        if((Integer.parseInt(dt[2])-Integer.parseInt(dtOfBt[2]))<13){
                            Toast.makeText(SignUpActivity.this,"Age should be greater than 13",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                if (SFname.getText().toString().equals("") || SLname.getText().toString().equals("") || SEmail.getText().toString().equals("") || SPassword.getText().toString().equals("") || SCPassword.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Enter all the Fields", Toast.LENGTH_SHORT).show();
                } else {

                    user = new Profile();
                    user.setFirstname(SFname.getText().toString());
                    user.setLastname(SLname.getText().toString());
                    user.setEmail(SEmail.getText().toString());
                    user.setPassword(SPassword.getText().toString());
                    user.setBirthday(Sbirthday.getText().toString());



                    //Log.d("Created",user.toString());


                    mAuth.createUserWithEmailAndPassword(SEmail.getText().toString(), SPassword.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        DatabaseReference newRef = childref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        user.setId(mAuth.getCurrentUser().getUid());

                                        newRef.setValue(user);
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();


                                        if (firebaseUser != null) {
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(user.getFirstname()).build();
                                            firebaseUser.updateProfile(profileUpdates);
                                           // Log.d("Display Name",firebaseUser.getDisplayName());
                                        }
                                        Toast.makeText(SignUpActivity.this, "Succesfully Created User", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
