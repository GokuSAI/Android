package edu.uncc.notekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sai Yesaswy on 2/26/2017.
 */

public class NoteDAO {
    private SQLiteDatabase db;

    private static final String[] allColumns = {
            NotesTable.COLUMN_ID,
            NotesTable.COLUMN_TASKNOTE,
            NotesTable.COLUMN_STATUS,
            NotesTable.COLUMN_PRIORITY,
            NotesTable.COLUMN_UPDATETIME};

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_TASKNOTE,note.getTaskNote());
        values.put(NotesTable.COLUMN_STATUS,note.getStatus());
        values.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        values.put(NotesTable.COLUMN_UPDATETIME,formatter.format(note.getCreatedTime()));
        return db.insert(NotesTable.TABLENAME,null,values);
    }

    public boolean update(Note note){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_TASKNOTE,note.getTaskNote());
        values.put(NotesTable.COLUMN_STATUS,note.getStatus());
        values.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        values.put(NotesTable.COLUMN_UPDATETIME,formatter.format(note.getCreatedTime()));
        return db.update(NotesTable.TABLENAME,values,NotesTable.COLUMN_ID + "=?",new String[]{note.getId()+""})>0;
    }

    public boolean delete(Note note){
        return db.delete(NotesTable.TABLENAME,NotesTable.COLUMN_ID + "=?",new String[]{note.getId()+""})>0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Note get(long id) throws ParseException {
        Note note = null;
        Cursor c = db.query(true,NotesTable.TABLENAME,allColumns, NotesTable.COLUMN_ID + "=?",new String[]{id+""},null,null,null,null,null);
        if(c!=null && c.moveToFirst() ){
            note = buildNoteFromCursor(c);
        }
        if(!c.isClosed()){
            c.close();
        }
        return note;
    }

    public List<Note> getAll() throws ParseException {
        List<Note> noteList = new ArrayList<Note>();

        Cursor c = db.query(NotesTable.TABLENAME,allColumns, null,null,null,null,null);

        if(c!=null && c.moveToFirst()){
            do{
                Note note = buildNoteFromCursor(c);
                if(note!=null){
                    noteList.add(note);
                }
            }while (c.moveToNext());
            if(!c.isClosed()){
                c.close();
            }
        }
        return noteList;
    }

    private Note buildNoteFromCursor(Cursor c) throws ParseException {
        Note note = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(c!=null){
            note = new Note();
            note.setId(c.getLong(0));
            note.setTaskNote(c.getString(1));
            note.setStatus(c.getString(2));
            note.setPriority(c.getString(3));
            note.setCreatedTime(formatter.parse(c.getString(4)));

        }
        return note;
    }
}
