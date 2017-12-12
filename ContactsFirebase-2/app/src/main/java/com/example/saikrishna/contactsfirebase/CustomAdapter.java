package com.example.saikrishna.contactsfirebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by saikrishna on 11/20/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    static public List<User> UserList;
    public Context mContext;

    int rsID;
    Intent playActivityIntent;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("çontacts");



    public CustomAdapter(Context context, List<User> UserList) {
        this.UserList = UserList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listviwelayout,parent,false);

        return new MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, int position) {


        Log.d("Adapter",UserList.toString());
        final User note = UserList.get(position);


        holder.name.setText(note.getfirstname()+ " "+ note.getlastname());
        holder.Email.setText(note.getemail());
        holder.Phone.setText(Integer.toString(note.getphone()));



        holder.dp.setImageResource(rsID);
        Picasso.with(mContext).load(note.getUrl()).into(holder.dp);






    }


    @Override
    public int getItemCount() {
        if(UserList == null){
            return 0;
        }
        return UserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView name,Email,Phone,dept;
        CheckBox cb;
        ImageView dp,delete,edit;
        public MyViewHolder(final View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            Phone=(TextView)itemView.findViewById(R.id.phone);
            Email = (TextView)itemView.findViewById(R.id.email);
            dp = (ImageView)itemView.findViewById(R.id.CourseManagerdp);
            delete = (ImageView)itemView.findViewById(R.id.delete);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User note = UserList.get(getAdapterPosition());
                    Intent intent = new Intent(mContext,DisplayActivity.class);
                   intent.putExtra("LIST",note);
                    mContext.startActivity(intent);


                }
            });
           delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final int position = getAdapterPosition();
                   AlertDialog dialog = new AlertDialog.Builder(mContext)
                           .setTitle("do u wanna delete?")
                           .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   User note = UserList.get(position);

                                   String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                   //String ky = dataref.child("contacts").child(id).child(note.getid());
                                   CustomAdapter.UserList.remove(getAdapterPosition());
                                   notifyDataSetChanged();

                               //    notifyItemRemoved(position);
                                 //  notifyItemRangeChanged(position, UserList.size());


                                   dataref.child("contacts").child(id).child(note.getid()).removeValue();



//                                   String email= UserList.get(position).getemail();
//
////                                    String idnote = iid.substring(49);
//
//
//                                   childref.child(email).removeValue();
                                   //

                               }
                           })
                           .setNegativeButton("no", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {

                               }
                           })
                           .create();

                   dialog.show();
               }
           });



                }

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                    int x = getAdapterPosition();
//                    Intent intent = new Intent(mContext,DisplayActivity.class);
//                    intent.putExtra("SENT",x);
//                    mContext.startActivity(intent);
//
//
//                }
//            });
        }





    }

