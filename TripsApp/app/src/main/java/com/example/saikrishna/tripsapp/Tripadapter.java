package com.example.saikrishna.tripsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by saikrishna on 12/4/17.
 */

public class Tripadapter extends RecyclerView.Adapter<Tripadapter.myViewHolder> {

    private Context tripcontext;
    private  final LayoutInflater inflater;
    List<NearbyCities> tripdata;

    public Tripadapter(Context context, List<NearbyCities> placeslist) {
        inflater = LayoutInflater.from(context);
        tripcontext = context;
        tripdata = placeslist;
    }

    @Override
    public Tripadapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.nearbyplaceslayout,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;

    }
    public Context getContext()
    {
        return tripcontext;
    }

    @Override
    public void onBindViewHolder(Tripadapter.myViewHolder holder, int position) {
            final NearbyCities nbc = tripdata.get(position);
            holder.name.setText(nbc.getDescription());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AddTrip.placename.setText(nbc.getDescription());

            }
        });
    }

    @Override
    public int getItemCount() {
        if(tripdata!=null){
        return tripdata.size();
        }
        else{
            return 0;
        }
    }
    class myViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView name;
        public myViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.textView);

        }

        @Override
        public void onClick(View v) {



        }
    }

    }
