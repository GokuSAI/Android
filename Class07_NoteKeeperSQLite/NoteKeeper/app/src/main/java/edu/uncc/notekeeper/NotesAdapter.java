package edu.uncc.notekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NANDU on 20-02-2017.
 */

public class NotesAdapter extends ArrayAdapter<Note> {
    List<Note> mData;
    Context mContext;
    int mResource;

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.mData=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }
        final Note note = mData.get(position);
        final DatabaseDataManager dbManager = new DatabaseDataManager(getContext());

        TextView textViewNote = (TextView)convertView.findViewById(R.id.tvNoteText);
        TextView textViewPriority = (TextView)convertView.findViewById(R.id.tvPriority);
        final CheckBox cbStatus = (CheckBox)convertView.findViewById(R.id.cbStatus);
        TextView textViewTime = (TextView)convertView.findViewById(R.id.tvUpdatedOn);

        PrettyTime p  = new PrettyTime();

        textViewNote.setText(note.getTaskNote());
        textViewPriority.setText(note.getPriority());
        textViewTime.setText(p.format(note.getCreatedTime()));

        cbStatus.setChecked(note.getStatus().equals("completed"));

        cbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Are you really want mark is as pending??")
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    note.setStatus("pending");
                                    note.setCreatedTime(Calendar.getInstance().getTime());
                                    dbManager.saveNote(note);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    cbStatus.setChecked(true);
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
                    note.setStatus("completed");
                    note.setCreatedTime(Calendar.getInstance().getTime());
                    dbManager.saveNote(note);
                }

            }
        });

        return convertView;
    }

    private Boolean IsNullorEmpty(String str)
    {
        return (str == null || str.isEmpty());
    }

}
