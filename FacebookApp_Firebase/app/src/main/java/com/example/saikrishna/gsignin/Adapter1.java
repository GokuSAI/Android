package com.example.saikrishna.gsignin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by saikrishna on 11/21/17.
 */

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {

    ArrayList<Profile> mData;
    Context activity;

    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref=dataref.child("users");
    DatabaseReference currentUserProfile = childref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    Profile curr;

    ArrayList<Post> ClickedUserposts = new ArrayList<>();

    public Adapter1(ArrayList<Profile> mData, Context activity) {
        Log.d("receivedlist",mData.toString());
        this.mData = mData;
        this.activity = activity;
        currentUserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                curr = dataSnapshot.getValue(Profile.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public Adapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.section3,parent,false );
        Adapter1.ViewHolder viewHolder = new Adapter1.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter1.ViewHolder holder, final int position) {


        Profile contacts = mData.get(position);
        Log.d("section1",mData.toString());
        holder.name.setText(contacts.getFirstname()+ " "+ contacts.getLastname());


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Profile friend = mData.get(position);
                ArrayList<String> req = friend.getRecievedList();
                req.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                childref.child(friend.getId()).setValue(friend);


                ArrayList<String> sent = curr.getSentList();
                sent.add(friend.getId());
                currentUserProfile.setValue(curr);

                mData.remove(position);
                notifyDataSetChanged();



            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.contact = contact;
            name = (TextView)itemView.findViewById(R.id.Section3FriendsNameTextview);
            img = (ImageView) itemView.findViewById(R.id.Section3FriendICon);


            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
                final ArrayList<Post> ClickedUserposts = new ArrayList<>();
                DatabaseReference postref;
                DatabaseReference childref2 = dataref.child("posts");
                Profile prof =mData.get(getAdapterPosition());



                postref =childref2.child(prof.getId());

                postref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ClickedUserposts.clear();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            //if(d.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))

                            ClickedUserposts.add(d.getValue(Post.class));


                        }

                        Intent i = new Intent(Adapter1.this.activity,ClickedUser.class);
                        i.putExtra("lane", ClickedUserposts);

                        Adapter1.this.activity.startActivity(i);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

             }


            });
        }

    }






}