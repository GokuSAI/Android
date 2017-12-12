package edu.uncc.notekeeper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/*
a.Assignment #. Homework 06
b.File Name.  InClass07_Group09.zip
c.Name. Sunand Kumar Pujari, Sai Yesaswy Mylavarapu
*/

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    EditText etNotes;
    Spinner spPriority;
    List<Note> notesList;
    Note note;
    DatabaseDataManager dbManager;
    ListView listView;
    HashMap<String,String> priorityMap = new HashMap<String, String>(){
        {
            put("High","High priority");
            put("Medium","Medium priority");
            put("Low","Low priority");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));

        dbManager = new DatabaseDataManager(this);

        etNotes = (EditText)findViewById(R.id.etNote);
        listView =(ListView)findViewById(R.id.listviewNotes);
        spPriority =(Spinner)findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_map, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        notesList= new ArrayList<>();
        try {
            notesList = dbManager.getAllNotes();
            Log.d("demo",notesList.size()+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.sort(notesList,new CompareByStatus());
        FillAppsList(notesList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsNullorEmpty(etNotes.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please Add a Note",Toast.LENGTH_SHORT).show();
                }
                else {
                    String priority = spPriority.getSelectedItem().toString();
                    if(priority.equals("Priority")){
                        Toast.makeText(getApplicationContext(),"Please set priority",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    note = new Note();
                    note.setTaskNote(etNotes.getText().toString());
                    note.setPriority(priorityMap.get(priority));
                    note.setStatus("pending");
                    note.setCreatedTime(Calendar.getInstance().getTime());

                    dbManager.saveNote(note);

                    try {
                        notesList = dbManager.getAllNotes();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    FillAppsList(notesList);

                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you really want to delete the task??")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                note = notesList.get(position);
                                dbManager.deleteNote(note);

                                try {
                                    notesList = dbManager.getAllNotes();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                FillAppsList(notesList);
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
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.show_all:
                try {
                    notesList = dbManager.getAllNotes();
                    Collections.sort(notesList,new CompareByStatus());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                FillAppsList(notesList);
                break;
            case R.id.show_completed:
                List<Note> completedNotes = new ArrayList<>();
                try {
                    notesList = dbManager.getAllNotes();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (Note note:notesList) {
                    if(note.getStatus().equals("completed"))
                        completedNotes.add(note);
                }

                notesList = completedNotes;
                FillAppsList(notesList);
                break;
            case R.id.show_pending:
                List<Note> pendingNotes = new ArrayList<>();
                try {
                    notesList = dbManager.getAllNotes();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (Note note:notesList) {
                    if(note.getStatus().equals("pending"))
                        pendingNotes.add(note);
                }
                notesList = pendingNotes;
                FillAppsList(notesList);
                break;
            case R.id.sort_by_priority:
                Collections.sort(notesList,new CompareByPriority());
                FillAppsList(notesList);
                break;
            case R.id.sort_by_time:
                Collections.sort(notesList,new CompareByTime());
                FillAppsList(notesList);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void FillAppsList(final List<Note> notesList){
        ArrayAdapter notesAdapter = new NotesAdapter(this,R.layout.row_item_layout,notesList);
        listView.setAdapter(notesAdapter);
        notesAdapter.setNotifyOnChange(true);

    }

    private Boolean IsNullorEmpty(String str)
    {
        return (str == null || str.isEmpty());
    }


    private class CompareByStatus implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return (o1.getStatus().equals(o2.getStatus()))?0:(o1.getStatus().equals("pending"))?-1:1 ;
        }
    }

    private class CompareByPriority implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return (o1.getPriority()==o2.getPriority())?0:(o1.getPriority().equals("Low priority"))?-1:(o1.getPriority().equals("Medium priority"))?-1:1 ;
        }
    }

    private class CompareByTime implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return (o1.getCreatedTime()==o2.getCreatedTime())?0:(o1.getCreatedTime().after(o2.getCreatedTime()))?-1:1 ;
        }
    }

}
