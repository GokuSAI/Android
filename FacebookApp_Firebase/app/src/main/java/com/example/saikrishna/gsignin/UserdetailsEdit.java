package com.example.saikrishna.gsignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserdetailsEdit extends AppCompatActivity {
EditText userfirstname,userlastname,userpass,userrepeatpass,userBirthday;
    Button savechanges;
    Profile useredit;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails_edit);
        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        userfirstname= (EditText)findViewById(R.id.UserEditFirstName);
        userlastname=(EditText)findViewById(R.id.UserEditLastName);
        userpass=(EditText)findViewById(R.id.UserEditPassword);
        userrepeatpass=(EditText)findViewById(R.id.UserEditRepeatPassword);
        userBirthday=(EditText)findViewById(R.id.UserEditBirthday);
        savechanges=(Button)findViewById(R.id.SaveChangesButton);


        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userfirstname.getText().toString().equals("") || userlastname.getText().toString().equals("")  || userpass.getText().toString().equals("") || userrepeatpass.getText().toString().equals("") || userBirthday.getText().toString().equals("")) {
                    Toast.makeText(UserdetailsEdit.this, "Enter all the Fields", Toast.LENGTH_SHORT).show();
                } else {

                    useredit = new Profile();
                    useredit = new Profile();
                    useredit.setFirstname(userfirstname.getText().toString());
                    useredit.setLastname(userlastname.getText().toString());
                    useredit.setPassword(userpass.getText().toString());
                    useredit.setBirthday(userBirthday.getText().toString());



                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    dataref.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(useredit);
                            //dialog.dismiss();
//                            Intent intent = new Intent(DisplayActivity.this, ContactsActivity.class);
//                            //intent.putExtra(MainActivity.user_data,AllUsers);
//                            startActivity(intent);


                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("User", databaseError.getMessage());
                        }
                    });
                }

                }
        });
    }
}
