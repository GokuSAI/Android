package com.example.saikrishna.contactsfirebase;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    CustomAdapter adapter;
    TextView usernametextview;
    static ArrayList<User> p;
    static ArrayList<User> p1;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref,childref1;
    ImageView addimage,editimage;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:


                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ContactsActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        p = new ArrayList<>();
//        if(getIntent().getExtras()!=null){
//
//            p = (ArrayList<User>) getIntent().getSerializableExtra(MainActivity.LIST);
//            //Log.d("INtente",p.toString());
//        }



        addimage = (ImageView)findViewById(R.id.imageView2);
        editimage=(ImageView)findViewById(R.id.imageView);
        usernametextview=(TextView)findViewById(R.id.UserNameTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("USERNAME",user.toString());
         //usernametextview.setText(user.getDisplayName() );

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                usernametextview.setText(profile.getDisplayName());
                String email = profile.getEmail();
            };
        }








        //------retrieving posts-----//
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //p = new ArrayList<>();

        childref = dataref.child("contacts").child(id);


        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                p.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.d("DEMO",ds.getValue().toString());

                    User note = ds.getValue(User.class);
                    //Log.d("BEFORE",p.toString());
                    p.add(note);
                    adapter.notifyDataSetChanged();

                    //Log.d("demo5",p.toString());

                    //for(DataSnapshot dsc : ds.getChildren()){



                    // Log.d("deom ", dsc.getValue().toString());
                    // }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this,CreateNewContact.class);
                startActivity(intent);
            }
        });




        adapter = new CustomAdapter(ContactsActivity.this,p);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}
