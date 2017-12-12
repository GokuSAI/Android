package com.example.saikrishna.firebasenotekeeper;

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
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

/**
 * Created by saikrishna on 11/12/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    public List<Note> NoteList;
    public Context mContext;
    Intent playActivityIntent;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref = dataref.child("notes");



    public NoteAdapter(Context context, List<Note> NoteList) {
        this.NoteList = NoteList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlayout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, int position) {


        final Note note = NoteList.get(position);
        holder.note.setText(note.getTaskNote());
        holder.priority.setText(note.getPriority());

        PrettyTime p  = new PrettyTime();
        holder.time.setText(p.format(note.getCreatedTime()));

        holder.cb.setChecked(note.getStatus().equals("completed"));


        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("pending ??")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note.setStatus("pending");
                                    note.setCreatedTime(Calendar.getInstance().getTime());

                                    String id = note.getId();
                                    String idnote = id.substring(id.indexOf("Notes/-") + 6).trim();

                                    childref.child(idnote).setValue(note);

                                    Log.d("Demo8", idnote);
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.cb.setChecked(true);

                                }
                            }).create();

                }
                else{
                    note.setStatus("completed");
                    note.setCreatedTime(Calendar.getInstance().getTime());
                    String id = note.getId();
                    String idnote = id.substring(id.indexOf("Notes/-")+6).trim();

                    childref.child(idnote).setValue(note);
                }
            }

        });



        }


    @Override
    public int getItemCount() {
        return NoteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener {

        public TextView note,priority,time;
        CheckBox cb;
        public MyViewHolder(View itemView) {
            super(itemView);

            note = (TextView)itemView.findViewById(R.id.NoteTextView);
            priority=(TextView)itemView.findViewById(R.id.PriorityTextView);
            time = (TextView)itemView.findViewById(R.id.TimeTextView);
            cb = (CheckBox)itemView.findViewById(R.id.StatusCheckBox);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            final int position = getAdapterPosition();
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("do u wanna delete?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Note note = NoteList.get(position);

                            String id = note.getId();
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
    }
}
