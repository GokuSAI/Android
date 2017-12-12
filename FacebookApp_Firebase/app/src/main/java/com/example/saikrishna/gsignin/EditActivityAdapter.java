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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

/**
 * Created by saikrishna on 11/21/17.
 */

public class EditActivityAdapter extends RecyclerView.Adapter<EditActivityAdapter.MyViewHolder> {

    static public List<Post> UserList;
    public Context mContext;

    int rsID;
    Intent playActivityIntent;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("posts");


    public EditActivityAdapter(Context context, List<Post> UserList) {
        this.UserList = UserList;
        this.mContext = context;
    }

    @Override
    public EditActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editlayout, parent, false);

        return new EditActivityAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final EditActivityAdapter.MyViewHolder holder, final int position) {


        Log.d("Adapter", UserList.toString());
        final Post note = UserList.get(position);

        holder.editname.setText(note.getName());
        holder.editContent.setText(note.getContent());

        PrettyTime p = new PrettyTime();

        holder.edittime.setText(p.format(note.getTime()));


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("do u wanna delete?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    childref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                            child(UserList.get(position).getId()).removeValue();

                                    UserList.remove(position);
                                    notifyDataSetChanged();

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


    @Override
    public int getItemCount() {
        if (UserList == null) {
            return 0;
        }
        return UserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView editname, edittime, editContent;

        ImageView del;

        public MyViewHolder(final View itemView) {
            super(itemView);

            editname = (TextView)itemView.findViewById(R.id.EditUNTextview);
            editContent=(TextView)itemView.findViewById(R.id.EditcontentTextview);
            edittime = (TextView)itemView.findViewById(R.id.EditTimeTextview);
            del = (ImageView)itemView.findViewById(R.id.EditDeleteImageview);
//            dept = (TextView) itemView.findViewById(R.id.DeptTextView);
//            dp = (ImageView)itemView.findViewById(R.id.dp);
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