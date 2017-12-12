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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.support.constraint.R.id.parent;


/**
 * Created by saikrishna on 12/3/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref= dataref.child("trips");


    private Context mcontext;
    private  final LayoutInflater inflater;
    List<Places> mdata;

    public CustomAdapter(Context context, List<Places> list){
        inflater = LayoutInflater.from(context);
        mcontext = context;
        mdata = list;


    }
    public Context getContext()
    {
        return mcontext;
    }


    @Override
    public CustomAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_layout,parent,false);
        myViewHolder holder=new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.myViewHolder holder, int position) {
        //Log.d("INside",mdata.toString());








        final Places places = mdata.get(position);
        holder.tripname.setText(places.getName());
        holder.placename.setText(places.getPlace());

        holder.addplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,AddPlaces.class);
               intent.putExtra("Place",places.getPlace());
                intent.putExtra("ID",places.getId());


                mcontext.startActivity(intent);
            }
        });
//
//        if(places.areas != null) {
//            for (area comment : places.areas) {
//
//                LinearLayout commentView = new LinearLayout(getContext());
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                commentView.setLayoutParams(params);
//                params.setMargins(0, 0, 0, 10);
//                commentView.setOrientation(LinearLayout.VERTICAL);
//                commentView.setWeightSum(2);
//                LinearLayout messageView = new LinearLayout(getContext());
//                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
//                messageView.setLayoutParams(params);
//
//                TextView msgTxt = new TextView(getContext());
//                msgTxt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                msgTxt.setText(comment.getAreaname());
//                messageView.addView(msgTxt);
//
//                holder.extralayout.addView(commentView);
//            }
//        }




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
TextView tripname,placename;
        ImageView addplaces,maps;
        LinearLayout extralayout;

        public  myViewHolder(View itemview){
            super(itemview);
            tripname = (TextView)itemview.findViewById(R.id.Trip_textview);
            placename = (TextView)itemview.findViewById(R.id.Place_textview);
            addplaces = (ImageView)itemview.findViewById(R.id.AddPlace_imageview);
            maps=(ImageView)itemview.findViewById(R.id.Place_imageview);
            extralayout = itemview.findViewById(R.id.LinearLayout);

        }
        @Override
        public void onClick(View v) {


        }
    }



}
