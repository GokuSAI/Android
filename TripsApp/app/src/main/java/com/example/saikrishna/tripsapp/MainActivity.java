package com.example.saikrishna.tripsapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

CustomAdapter adapter1;
RecyclerView main_recycler_view;
    ArrayList<Places> placeslist;
    ImageView home,addnewplaces;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        childref= dataref.child("trips");
        placeslist= new ArrayList<>();

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Places> tempplaceslist = new ArrayList<>();


                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    //placeslist = new ArrayList<Places>();

                    Places places = ds.getValue(Places.class);
                    tempplaceslist.add(places);
                    //Log.d("INDI",places.toString());

                }
                placeslist =tempplaceslist;
                Log.d("CUSTOM",placeslist.toString());
                CallAdapter(placeslist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        home = (ImageView)findViewById(R.id.Home_imageview);
        addnewplaces=(ImageView)findViewById(R.id.Addnewplaces_imageview);
        addnewplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTrip.class);
                startActivity(intent);

            }
        });



        main_recycler_view = (RecyclerView)findViewById(R.id.Main_recycler_view);


    }

    public  void CallAdapter(List<Places> functionlist){
        Log.d("CUSTOM2",functionlist.toString());
        main_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter adapter1 = new CustomAdapter(this,functionlist);
        main_recycler_view.setAdapter(adapter1);
    }
}
