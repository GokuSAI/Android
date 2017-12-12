package com.example.saikrishna.gsignin;

import android.content.Context;
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

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {

    ArrayList<Profile> mData;
    Context activity;

    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref=dataref.child("users");
    DatabaseReference currentUserProfile = childref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    Profile curr;

    public Adapter3(ArrayList<Profile> mData,Context activity) {
        Log.d("receivedlist",mData.toString());
        this.mData = mData;
        this.activity = activity;



        currentUserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                curr = dataSnapshot.getValue(Profile.class);
                Fragment3.revievedRequests = curr.getRecievedList();
                Fragment1.friends = curr.getFriendlist();
                Fragment2.curr = curr;
                Home.friendsList = curr.getFriendlist();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public Adapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.section2,parent,false );
        Adapter3.ViewHolder viewHolder = new Adapter3.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter3.ViewHolder holder, final int position) {


        Profile contacts = mData.get(position);

        Log.d("section1",mData.toString());
        holder.name.setText(contacts.getFirstname() + " " + contacts.getLastname());


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile fr = mData.get(position);
                ArrayList<String> friendsCurr = curr.getFriendlist();
                friendsCurr.add(fr.getId());

                ArrayList<String> friendRecv = fr.getFriendlist();
                friendRecv.add(curr.getId());

                ArrayList<String> cuurRecList = curr.getRecievedList();
                cuurRecList.remove(fr.getId());


                ArrayList<String> sendFList = fr.getSentList();
                sendFList.remove(curr.getId());

                mData.remove(position);
                notifyDataSetChanged();

                //safe logic
                Fragment3.revievedRequests = curr.getRecievedList();
                Fragment1.friends = curr.getFriendlist();
                Fragment2.curr = curr;
                Home.friendsList = curr.getFriendlist();
                //safe end

                //Fragment3.revievedRequests = curr.getRecievedList();
                currentUserProfile.setValue(curr);
                childref.child(fr.getId()).setValue(fr);






            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile fr = mData.get(position);
                ArrayList<String> cuurRecList = curr.getRecievedList();
                cuurRecList.remove(fr.getId());


                ArrayList<String> sendFList = fr.getSentList();
                sendFList.remove(curr.getId());

                mData.remove(position);
                notifyDataSetChanged();

                //safe logic
                Fragment3.revievedRequests = curr.getRecievedList();
                Fragment1.friends = curr.getFriendlist();
                Fragment2.curr = curr;
                Home.friendsList = curr.getFriendlist();
                //safe end

               // Fragment3.revievedRequests = cuurRecList;
                currentUserProfile.setValue(curr);
                childref.child(fr.getId()).setValue(fr);



            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView accept,delete;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.contact = contact;
            name = (TextView)itemView.findViewById(R.id.FriendsNameSection2TextView);
            delete = (ImageView) itemView.findViewById(R.id.decline);
            accept = (ImageView) itemView.findViewById(R.id.accept);

        }

    }


}