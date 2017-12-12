package com.example.saikrishna.gsignin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

/**
 * Created by saikrishna on 11/21/17.
 */

public class ClickedUserAdapter extends RecyclerView.Adapter<ClickedUserAdapter.MyViewHolder> {

    static public List<Post> UserList;
    public Context mContext;

    int rsID;
    Intent playActivityIntent;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("posts");


    public ClickedUserAdapter(Context context, List<Post> UserList) {
        this.UserList = UserList;
        this.mContext = context;
    }

    @Override
    public ClickedUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clickeduserlayout, parent, false);

        return new ClickedUserAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ClickedUserAdapter.MyViewHolder holder, final int position) {


        Log.d("Adapter", UserList.toString());
        final Post note = UserList.get(position);

        holder.uname.setText(note.getName());
        holder.uContent.setText(note.getContent());

        PrettyTime p = new PrettyTime();

        holder.utime.setText(p.format(note.getTime()));




    }


    @Override
    public int getItemCount() {
        if (UserList == null) {
            return 0;
        }
        return UserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView uname, utime, uContent;

        ImageView del;

        public MyViewHolder(final View itemView) {
            super(itemView);

            uname = (TextView)itemView.findViewById(R.id.ClickedLayoutNameTextview);
            uContent=(TextView)itemView.findViewById(R.id.ClickedLayoutContentTextview);
            utime = (TextView)itemView.findViewById(R.id.ClickedLayoutTimeTextview);



        }
    }
}