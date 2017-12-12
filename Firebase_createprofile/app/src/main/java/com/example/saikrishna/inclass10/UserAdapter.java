package com.example.saikrishna.inclass10;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 11/13/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

   static public List<User> UserList;
    public Context mContext;

    int rsID;
    Intent playActivityIntent;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("notes");



    public UserAdapter(Context context, List<User> UserList) {
        this.UserList = UserList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listviewlayout,parent,false);

        return new MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, int position) {


        Log.d("Adapter",UserList.toString());
        final User note = UserList.get(position);


        holder.name.setText(note.getfirstname());
        holder.Email.setText(note.getemail());
        holder.Phone.setText(note.getphone());
        holder.dept.setText(note.getdept());


holder.dp.setImageResource(rsID);






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
        ImageView dp;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.CreatePhoneEditText);
            Phone=(TextView)itemView.findViewById(R.id.PhoneTextView);
            Email = (TextView)itemView.findViewById(R.id.CreateEmailEdittext);
            dept = (TextView) itemView.findViewById(R.id.DeptTextView);
            dp = (ImageView)itemView.findViewById(R.id.dp);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final int position = getAdapterPosition();
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("do u wanna delete?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    User note = UserList.get(position);

                                    String id = note.getid();
                                    String idnote = id.substring(id.indexOf("Notes/-")+6).trim();


                                    childref.child(idnote).removeValue();
                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create();

                    dialog.show();
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    int x = getAdapterPosition();
                    Intent intent = new Intent(mContext,DisplayActivity.class);
                    intent.putExtra("SENT",x);
                    mContext.startActivity(intent);


                }
            });
        }


        public int getImage(String imgID){
            if(imgID.equals("img1")){
                rsID= R.drawable.avatar_f_1;
            }
            else if(imgID.equals("img2")){
                rsID=R.drawable.avatar_m_3;
            }
            else if(imgID.equals("img3")){
                rsID = R.drawable.avatar_f_2;
            }
            else if(imgID.equals("img4")){
                rsID = R.drawable.avatar_m_2;
            }
            else if(imgID.equals("img5")){
                rsID = R.drawable.avatar_f_3;
            }
            else if(imgID.equals("img6")){
                rsID = R.drawable.avatar_m_1;
            }
            return rsID;
        }


    }
}
