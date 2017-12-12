package edu.uncc.notekeeper_realmdb;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/*
Assignment InClass13
MainActivity.java
Sai Yesaswy Mylavarapu
 */

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    EditText etNotes;
    Spinner spPriority;
    List<Note> notesList;
    Note note;
    private Realm realm = Realm.getDefaultInstance();
    private RealmChangeListener realmListener;

    RecyclerView listView;
    HashMap<String,String> priorityMap = new HashMap<String, String>(){
        {
            put("High","High priority");
            put("Medium","Medium priority");
            put("Low","Low priority");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                ArrayList<Note> mainList = new ArrayList<>();
                RealmResults<Note> result = realm.where(Note.class)
                        .findAll();
                result = result.sort("priorityId");
                for(Note notes:result){
                    mainList.add(notes);
                }
                FillAppsList(mainList);
            }};
        realm.addChangeListener(realmListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setTitle("Notekeeper");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));

        etNotes = (EditText)findViewById(R.id.etNote);
        listView =(RecyclerView) findViewById(R.id.listviewNotes);

        spPriority =(Spinner)findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_map, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        notesList = new ArrayList<>();

        RealmResults<Note> result1 = realm.where(Note.class)
                .findAll();
        result1 = result1.sort("priorityId");
        for(Note notes:result1){
            notesList.add(notes);
        }
        FillAppsList(notesList);

//        FillAppsList(sortList((ArrayList<Note>) notesList));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsNullorEmpty(etNotes.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please Add a Note",Toast.LENGTH_SHORT).show();
                }
                else {
                    final String priority = spPriority.getSelectedItem().toString();
                    if(priority.equals("Priority")){
                        Toast.makeText(getApplicationContext(),"Please set priority",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            int nextId;
                            Number currentIdNum = realm.where(Note.class).max("note_id");
                            if(currentIdNum == null) {
                                nextId = 1;
                            } else {
                                nextId = currentIdNum.intValue() + 1;
                            }
                            Note note = realm.createObject(Note.class,nextId);
                            note.setTaskNote(etNotes.getText().toString());
                            note.setPriority(priorityMap.get(priority));
                            if(priorityMap.get(priority).equals("Low priority")){
                                note.setPriorityId(3);
                            }
                            else if(priorityMap.get(priority).equals("Medium priority")){
                                note.setPriorityId(2);
                            }
                            else if(priorityMap.get(priority).equals("High priority")){
                                note.setPriorityId(1);
                            }
                            note.setStatus("pending");
                            note.setCreatedTime(Calendar.getInstance().getTime());

                        }
                    });
                }
            }
        });
    }
//
//    public ArrayList<Note> sortList(ArrayList<Note> notelist){
//        ArrayList<Note> list1,list2;
//        list1 = new ArrayList<>();
//        list2 = new ArrayList<>();
//
//        for(int i=0;i<notelist.size();i++){
//            if(notelist.get(i).getStatus().equals("completed")){
//                list1.add(notelist.get(i));
//            }
//            else if(notelist.get(i).getStatus().equals("pending")){
//                list2.add(notelist.get(i));
//            }
//        }
//
//        Collections.sort(list1,new CompareByPriority());
//        Collections.sort(list2,new CompareByPriority());
//
//        for(int i=0;i<list1.size();i++){
//            list2.add(list1.get(i));
//        }
//
//        Log.d("demo9",list2.toString());
//
//        return list2;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.show_all:
                ArrayList<Note> list = new ArrayList<>();
                RealmResults<Note> result = realm.where(Note.class)
                        .findAll();
                result = result.sort("status", Sort.DESCENDING);
                for(Note notes:result){
                    list.add(notes);
                }
                FillAppsList(list);
                break;
            case R.id.show_completed:
                ArrayList<Note> completed = new ArrayList<>();
                RealmResults<Note> result2 = realm.where(Note.class).equalTo("status","completed").findAll();
                for(Note notes:result2){
                    completed.add(notes);
                }
                FillAppsList(completed);
                break;
            case R.id.show_pending:
                ArrayList<Note> pending = new ArrayList<>();
                RealmResults<Note> result3 = realm.where(Note.class).equalTo("status","pending").findAll();
                for(Note notes:result3){
                    pending.add(notes);
                }
                FillAppsList(pending);
                break;
            case R.id.sort_by_priority:
                ArrayList<Note> priorityList = new ArrayList<>();
                RealmResults<Note> result4 = realm.where(Note.class)
                        .findAll();
                result4 = result4.sort("priorityId");
                for(Note notes:result4){
                    priorityList.add(notes);
                }
                FillAppsList(priorityList);
                break;
            case R.id.sort_by_time:
                ArrayList<Note> timeList = new ArrayList<>();
                RealmResults<Note> result5 = realm.where(Note.class)
                        .findAll();
                result5 = result5.sort("createdTime",Sort.DESCENDING);
                for(Note notes:result5){
                    timeList.add(notes);
                }
                FillAppsList(timeList);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void FillAppsList(final List<Note> notesList){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        NoteAdapter adapter = new NoteAdapter(this,notesList);
        listView.setAdapter(adapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
