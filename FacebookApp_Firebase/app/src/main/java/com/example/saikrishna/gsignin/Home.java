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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Home extends AppCompatActivity {

    TextView uname;
    RecyclerView mRecyclerView;
    Homeadapter adapter;
    EditText content;
    ImageView postimageview,friendslistimageview;
    String postcontent,userEmail;
    Post post;
    static Profile curr;
    static ArrayList<String> friendsList;
    ArrayList<Post> Allposts = new ArrayList<>();
    ArrayList<Profile> AllUsers = new ArrayList<>();
    ArrayList<Post> totalposts = new ArrayList<>();


    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref,childref2,childref3;
    DatabaseReference currentUserProfile = dataref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    private FirebaseAuth mAuth;
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
                Intent intent = new Intent(Home.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        uname = (TextView)findViewById(R.id.UserNameTextView);
        content = (EditText)findViewById(R.id.postEditText);
        postimageview=(ImageView)findViewById(R.id.imageView4);
        friendslistimageview = (ImageView)findViewById(R.id.FrndsListImageView);


            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //Log.d("Display Name",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        uname.setText(Home.curr.getFirstname()+" "+Home.curr.getLastname());
        uname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,EditActivity.class);
                startActivity(intent);
            }
        });
        friendslistimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,TabActivity.class);
                startActivity(intent);
            }
        });



        childref3=dataref.child("posts");


        childref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Allposts.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    //if(d.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    if(Home.friendsList.contains(d.getKey()) || curr.getId().equals(d.getKey()) ) {

                        for (DataSnapshot d1 : d.getChildren()) {
                            Post prof = d1.getValue(Post.class);
                            //if(prof.getEmail().equals(userEmail)){
                            Allposts.add(prof);
                            totalposts.add(prof);
                            adapter.notifyDataSetChanged();

                            //}

                        }
                    }
                }
                Log.d("total posts",totalposts.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//
//        //------retrieving posts-----//
//        childref = dataref.child("posts").child(id);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
//             userEmail= user.getEmail();
//
//            Log.d("NAME", FirebaseAuth.getInstance().getCurrentUser().getUid());
//        }

//
//
//        childref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//
//                    //Log.d("DEMO",ds.getValue().toString());
//
//                    Post note = ds.getValue(Post.class);
//
//
//                    if(Allposts.contains(note)){
//
//                    }
//                    else{
//                        Allposts.add(note);
//
//                    }
//                    adapter.notifyDataSetChanged();
//
//                    //Log.d("demo5",note.toString());
//
//                    //for(DataSnapshot dsc : ds.getChildren()){
//
//
//
//                    // Log.d("deom ", dsc.getValue().toString());
//                    // }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        postimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(content.getText().toString()==null || content.getText().toString().equals("")){
                    Toast.makeText(Home.this, "Empty post", Toast.LENGTH_SHORT).show();
                }
                else {

                    postcontent = content.getText().toString();
                    post = new Post();
                    post.setContent(postcontent);
                    post.setUserid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    post.setName(Home.curr.getFirstname()+" " +Home.curr.getLastname());






                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    String time = Calendar.getInstance().getTime().toString();
                    Log.d("TIME", time);
                    post.setTime(Calendar.getInstance().getTime());

                    Allposts.add(post);

                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String ky = dataref.child("posts").child(id).push().getKey();
                    post.setId(ky);
                    dataref.child("posts").child(id).child(ky).setValue(post);

                }

            }
        });

       // Collections.sort(Allposts,new CompareByTime());
        Collections.sort(Allposts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (o1.getTime()==o2.getTime())?0:(o1.getTime().after(o2.getTime()))?-1:1 ;
            }
        });
        adapter = new Homeadapter(this,Allposts);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setAdapter(adapter);


    }


    private class CompareByTime implements Comparator<Post> {
        @Override
        public int compare(Post o1, Post o2) {
            Log.d("in compare", o1.getName());
            return (o1.getTime()==o2.getTime())?0:(o1.getTime().after(o2.getTime()))?-1:1 ;
        }
    }
}
