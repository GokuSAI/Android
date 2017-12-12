package com.example.saikrishna.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity {

    static ArrayList<User> p;
    Button CreateButton,Logout;
    RecyclerView mRecyclerView;
    UserAdapter adapter;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        if(getIntent().getExtras()!=null){
            p = (ArrayList<User>) getIntent().getSerializableExtra(MainActivity.LIST);
            //Log.d("INtente",p.toString());
        }


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        p = new ArrayList<>();
         adapter = new UserAdapter(this,p);

        childref = dataref.child("contacts").child(id);


        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.d("DEMO",ds.getValue().toString());

                    User note = ds.getValue(User.class);
                    p.add(note);
                    adapter.notifyDataSetChanged();

                    Log.d("demo5",note.toString());

                    //for(DataSnapshot dsc : ds.getChildren()){



                      // Log.d("deom ", dsc.getValue().toString());
                   // }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CreateButton = (Button)findViewById(R.id.CreateNewContactButton);
        Logout=(Button)findViewById(R.id.LogoutButton);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setAdapter(adapter);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsList.this, CreateNewContact.class);
                startActivity(intent);

            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ContactsList.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
