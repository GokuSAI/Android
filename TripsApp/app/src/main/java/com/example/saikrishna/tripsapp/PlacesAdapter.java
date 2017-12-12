package com.example.saikrishna.tripsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 12/4/17.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.myViewHolder> {
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref= dataref.child("trips");

    static ArrayList<area> areaslist;
    private Context mcontext;
    private  final LayoutInflater inflater;
    List<NearbyPlaces> mdata;
    String ID;

    public PlacesAdapter(Context context, List<NearbyPlaces> list,String randomid){
        inflater = LayoutInflater.from(context);
        mcontext = context;
        mdata = list;
        ID = randomid;

    }
    public Context getContext()
    {
        return mcontext;
    }


    @Override
    public PlacesAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.placeslayout,parent,false);
        PlacesAdapter.myViewHolder holder=new PlacesAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PlacesAdapter.myViewHolder holder, int position) {
        //Log.d("INside",mdata.toString());

        final NearbyPlaces places = mdata.get(position);
        holder.nearbyplace.setText(places.getName());
        Picasso.with(mcontext).load(places.getIcon()).into(holder.icon);


        areaslist = new ArrayList<>();
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Places places = new Places();
                DatabaseReference newRef = childref.child(ID);


                places.setAreas(holder.nearbyplace.getText().toString());
                Log.d("ID",ID);


                newRef.setValue(places);








            }
        });




    }

    @Override
    public int getItemCount() {
        if(mdata!=null) {
            return mdata.size();
        }
        else{
            return 0;
        }

    }

    class myViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView nearbyplace;
        ImageView icon,add;

        public  myViewHolder(View itemview){
            super(itemview);
           nearbyplace = itemview.findViewById(R.id.placelist_textview);
            icon =itemview.findViewById(R.id.Icon_imageview);
            add = itemview.findViewById(R.id.Addtolist_imageview);

        }
        @Override
        public void onClick(View v) {


        }
    }



}