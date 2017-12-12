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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    RecyclerView Editrecyclerview;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref,childref2,childref3;
    ArrayList<Post> totalposts = new ArrayList<>();
    ImageView editimage,friendsicon;
    String userEmail;
    TextView username;
    ArrayList<Post> Allposts = new ArrayList<>();
EditActivityAdapter adapter;
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
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        friendsicon=(ImageView)findViewById(R.id.imageView3);
        friendsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this,Home.class);
                startActivity(i);
                finish();
            }
        });
        username=(TextView)findViewById(R.id.EditUsernameTextview);
        Editrecyclerview = (RecyclerView)findViewById(R.id.EditRecyclerview);
        editimage = (ImageView)findViewById(R.id.EditImageview);
        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this,UserdetailsEdit.class);
                startActivity(intent);
            }
        });
        username.setText(Home.curr.getFirstname()+" "+Home.curr.getLastname());
        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String id  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("ID",id);


        //------retrieving posts-----//
        childref = dataref.child("posts").child(id);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
             userEmail= user.getEmail();

            Log.d("NAME", FirebaseAuth.getInstance().getCurrentUser().getUid());
        }



        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    //Log.d("DEMO",ds.getValue().toString());

                    Post note = ds.getValue(Post.class);


                    if(Allposts.contains(note)){

                    }
                    else{
                        Allposts.add(note);

                    }
                    adapter.notifyDataSetChanged();

                    //Log.d("demo5",note.toString());

                    //for(DataSnapshot dsc : ds.getChildren()){



                    // Log.d("deom ", dsc.getValue().toString());
                    // }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new EditActivityAdapter(this,Allposts);




        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Editrecyclerview.setLayoutManager(mLayoutManager);


        Editrecyclerview.setAdapter(adapter);

    }
}
