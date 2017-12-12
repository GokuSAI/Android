package com.example.saikrishna.firebasenotekeeper;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

EditText mnoteEdittext;
    TextView mnoteTextView,mpriorityTextView,mtimeTextView;
    CheckBox mstatusCheckbox;
    RecyclerView mRecyclerView;
    Button Add;
    Spinner priorityspinner;
    List<Note> notesList;
    Note note;
    String priority;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        childref = dataref.child("notes");

        mnoteEdittext = (EditText)findViewById(R.id.editTextNote);
        mnoteTextView =(TextView)findViewById(R.id.NoteTextView);
        mpriorityTextView=(TextView)findViewById(R.id.PriorityTextView);
        mtimeTextView = (TextView)findViewById(R.id.TimeTextView);
        mstatusCheckbox=(CheckBox)findViewById(R.id.StatusCheckBox);
        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        Add=(Button)findViewById(R.id.ADDBUTTON);
        Add.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));

        priorityspinner=(Spinner)findViewById(R.id.spinner);

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("demo5", String.valueOf(dataSnapshot.getChildrenCount()));
                ArrayList<Note> notesList1= new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Note note = ds.getValue(Note.class);
                    notesList1.add(note);
                    Log.d("demo5",note.toString());
                }
                Log.d("demo6",notesList1.toString());
                notesList = notesList1;
                FillAdapter(sortList((ArrayList<Note>) notesList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        final HashMap<String,String> priorityMap = new HashMap<String, String>(){
            {
                put("High","High priority");
                put("Medium","Medium priority");
                put("Low","Low priority");
            }
        };
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerentries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorityspinner.setAdapter(adapter);





        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = priorityspinner.getSelectedItem().toString();
                if(priority.equals("priority")){
                    Toast.makeText(MainActivity.this, "Select Priority", Toast.LENGTH_SHORT).show();
                }
                else{

                }
                note = new Note();
                note.setTaskNote(mnoteEdittext.getText().toString());
                note.setPriority(priorityMap.get(priority));
                note.setStatus("pending");
                note.setCreatedTime(Calendar.getInstance().getTime());
                Log.d("Created",note.toString());
                DatabaseReference newRef = childref.push();
                note.setId(String.valueOf(newRef));

                newRef.setValue(note);

                FillAdapter(notesList);
            }
        });

    }
    public ArrayList<Note> sortList(ArrayList<Note> notesList){
        ArrayList<Note> list1,list2;
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        for(int i=0;i<notesList.size();i++){
            if(notesList.get(i).getStatus().equals("completed")){
                list1.add(notesList.get(i));
            }
            else if(notesList.get(i).getStatus().equals("pending")){
                list2.add(notesList.get(i));
            }
        }

        Collections.sort(list1,new CompareByPriority());
        Collections.sort(list2,new CompareByPriority());

        for(int i=0;i<list1.size();i++){
            list2.add(list1.get(i));
        }

        Log.d("demo9",list2.toString());

        return list2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.show_all:
                FillAdapter(sortList((ArrayList<Note>) notesList));
                break;
            case R.id.show_completed:
                List<Note> completedNotes = new ArrayList<>();
                for (Note note:notesList) {
                    if(note.getStatus().equals("completed"))
                        completedNotes.add(note);
                }
                FillAdapter(completedNotes);
                break;
            case R.id.show_pending:
                List<Note> pendingNotes = new ArrayList<>();
                for (Note note:notesList) {
                    if(note.getStatus().equals("pending"))
                        pendingNotes.add(note);
                }
                FillAdapter(pendingNotes);
                break;
            case R.id.sort_by_priority:
                FillAdapter(sortList((ArrayList<Note>) notesList));
                break;
            case R.id.sort_by_time:
                Collections.sort(notesList,new CompareByTime());
                FillAdapter(notesList);
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void FillAdapter(final List<Note> notesList){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        NoteAdapter adapter = new NoteAdapter(this,notesList);
        mRecyclerView.setAdapter(adapter);
    }
    private class CompareByPriority implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            int i=0,j = 0;
            if(o1.getPriority().equals("Low priority")){
                i=1;
            }
            else if(o1.getPriority().equals("Medium priority")){
                i=5;
            }
            else if(o1.getPriority().equals("High priority")){
                i=10;
            }

            if(o2.getPriority().equals("Low priority")){
                j=1;
            }
            else if(o2.getPriority().equals("Medium priority")){
                j=5;
            }
            else if(o2.getPriority().equals("High priority")){
                j=10;
            }

            return (j-i);
            //return (o1.getPriority().equals(o2.getPriority()))?0:(o1.getPriority().equals("Low priority"))?-1:(o1.getPriority().equals("Medium priority"))?-1:1 ;
        }
    }

    private class CompareByTime implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return (o1.getCreatedTime()==o2.getCreatedTime())?0:(o1.getCreatedTime().after(o2.getCreatedTime()))?-1:1 ;
        }
    }
}
