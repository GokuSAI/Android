package edu.uncc.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Sai Yesaswy on 2/26/2017.
 */

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public NoteDAO getNoteDAO(){
        return this.noteDAO;
    }

    public long saveNote(Note note){
        return this.noteDAO.save(note);
    }

    public boolean updateNote(Note note){
        return this.noteDAO.update(note);
    }

    public boolean deleteNote(Note note){
        return this.noteDAO.delete(note);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Note getNote(long id) throws ParseException {
        return this.noteDAO.get(id);
    }

    public List<Note> getAllNotes() throws ParseException {
        return this.noteDAO.getAll();
    }
}
