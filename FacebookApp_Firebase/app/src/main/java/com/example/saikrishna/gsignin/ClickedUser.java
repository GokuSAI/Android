package com.example.saikrishna.gsignin;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClickedUser extends AppCompatActivity {

    TextView name,posts;
    ImageView homeicon;
    RecyclerView ClickedRecyclerview;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref,childref2,childref3;
  String userEmail;
    String s1,s2;
    ClickedUserAdapter Adapter;
    ArrayList<Post> Allposts = new ArrayList<>();
    ArrayList<Profile> Clickeduser = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_frnds, menu);


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:


                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ClickedUser.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_user);
        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if(getIntent().getExtras().get("lane")!=null){
            Allposts = (ArrayList<Post>) getIntent().getExtras().get("lane");
        }



        childref2=dataref.child("users").child(Allposts.get(0).getUserid());



        childref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Profile prof = dataSnapshot.getValue(Profile.class);

                s1 =prof.getFirstname()+prof.getLastname();
                s2=prof.getFirstname();


                doRemainingWork();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public void doRemainingWork(){

        name = (TextView)findViewById(R.id.ClickedUserTextView);
        posts=(TextView)findViewById(R.id.ClickedPostsTextView);

        name.setText(s1);
        posts.setText(s2+"'s posts");
        homeicon=(ImageView)findViewById(R.id.ClickedHomeImageview);


        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClickedUser.this,Home.class);
                startActivity(intent);
            }
        });
        ClickedRecyclerview=(RecyclerView)findViewById(R.id.ClickedUserRecyclerView);


        //------retrieving posts-----//
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        childref = dataref.child("posts").child(id);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            userEmail= user.getEmail();

            Log.d("NAME", FirebaseAuth.getInstance().getCurrentUser().getUid());
        }



        Adapter = new ClickedUserAdapter(this,Allposts);




        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        ClickedRecyclerview.setLayoutManager(mLayoutManager);


        ClickedRecyclerview.setAdapter(Adapter);


    }
}
