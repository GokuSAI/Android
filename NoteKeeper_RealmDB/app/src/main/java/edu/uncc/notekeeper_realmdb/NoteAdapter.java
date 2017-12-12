package edu.uncc.notekeeper_realmdb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

/*
Assignment InClass13
NoteAdapter.java
Sai Yesaswy Mylavarapu
 */


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{

    public List<Note> NoteList;
    public Context mContext;
    public Realm realm = Realm.getDefaultInstance();
    Intent playActivityIntent;

//    mp3Interface activity;

    public NoteAdapter(Context context, List<Note> NoteList) {
        this.NoteList = NoteList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Note note = NoteList.get(position);

        holder.textViewNote.setText(note.getTaskNote());
        holder.textViewPriority.setText(note.getPriority());

        PrettyTime p  = new PrettyTime();
        holder.textViewTime.setText(p.format(note.getCreatedTime()));

        holder.cbStatus.setChecked(note.getStatus().equals("completed"));

        holder.cbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("Do you really want to mark this as pending??")
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            note.setStatus("pending");
                                            note.setCreatedTime(Calendar.getInstance().getTime());
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.cbStatus.setChecked(true);
                                }
                            }).create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                        }
                    });
                    dialog.show();

                }
                else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            note.setStatus("completed");
                            note.setCreatedTime(Calendar.getInstance().getTime());
                        }
                    });

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return NoteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView textViewNote, textViewPriority,textViewTime;
        public CheckBox cbStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewNote = (TextView)itemView.findViewById(R.id.tvNoteText);
            textViewPriority = (TextView)itemView.findViewById(R.id.tvPriority);
            cbStatus = (CheckBox)itemView.findViewById(R.id.cbStatus);
            textViewTime = (TextView)itemView.findViewById(R.id.tvUpdatedOn);

            itemView.setOnLongClickListener(this);
        }

        public boolean onLongClick(View view) {
            final int position = getAdapterPosition();
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Are you really want to delete the task??")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Note note = NoteList.get(position);
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        note.deleteFromRealm();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).create();
                dialog.show();

            return false;
        }
    }

//    static public interface mp3Interface{
//        public void setupmp3(String mp3url,double duration);
//        public void closemp3();
//    }
}

