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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 11/18/17.
 */

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.MyViewHolder> {

    static public List<Post> UserList;
    public Context mContext;

    int rsID;
    Intent playActivityIntent;
    DatabaseReference postref;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("posts");
    ArrayList<Post> ClickedUserposts = new ArrayList<>();

    DatabaseReference childref2;
    public Homeadapter(Context context, List<Post> UserList) {
        this.UserList = UserList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linelayout, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        Log.d("Adapter", UserList.toString());
        final Post note = UserList.get(position);

        holder.name.setText(note.getName());
        holder.Content.setText(note.getContent());

        PrettyTime p = new PrettyTime();

        holder.time.setText(p.format(note.getTime()));

    }


    @Override
    public int getItemCount() {
        if (UserList == null) {
            return 0;
        }
        return UserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, time, Content, dept;
        CheckBox cb;
        ImageView dp;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            Content=(TextView)itemView.findViewById(R.id.post);
            time = (TextView)itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    Post prof =UserList.get(getAdapterPosition());

                    postref =childref.child(prof.getUserid());

                    postref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ClickedUserposts.clear();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                //if(d.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))

                                ClickedUserposts.add(d.getValue(Post.class));


                            }

                            Intent i = new Intent(mContext,ClickedUser.class);
                            i.putExtra("lane", ClickedUserposts);

                            mContext.startActivity(i);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });

                }
            });



//
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
////                    final int position = getAdapterPosition();
////                    AlertDialog dialog = new AlertDialog.Builder(mContext)
////                            .setTitle("do u wanna delete?")
////                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    User note = UserList.get(position);
////
////                                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                                    String ky = dataref.child("contacts").child(id).getKey();
////
////                                    dataref.child("contacts").child(id).removeValue();
////
////
////
////                                    String email= UserList.get(position).getemail();
////
//////                                    String idnote = iid.substring(49);
////
////
////                                    childref.child(email).removeValue();
////                                    UserAdapter.UserList.remove(getAdapterPosition());
////                                    notifyDataSetChanged();
////
////                                    notifyItemRemoved(position);
////                                    notifyItemRangeChanged(position, UserList.size());
////                                    //
////
////                                }
////                            })
////                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////
////                                }
////                            })
////                            .create();
////
////                    dialog.show();
////                    return false;
////                }
//            });
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
//        }


        }
    }
}
