package com.anjani.mad.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Anjani Reddy on 22-10-2017.
 */

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private AppDAO noteDAO;

    public DatabaseDataManager(Context con)
    {
        this.mContext = con;
        this.dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        this.db = dbOpenHelper.getWritableDatabase();
        noteDAO = new AppDAO(db);
    }

    public void close()
    {
        if(db != null)
            db.close();
    }

    public AppDAO getNoteDAO()
    {
        return this.noteDAO;
    }

    public long saveNote(AppData note){
        return this.noteDAO.save(note);
    }
    /*public boolean updateNote(AppData note){
        return this.noteDAO.update(note);
    }*/

    public boolean deleteNote(AppData note){
        return this.noteDAO.delete(note);
    }

    /*public AppData getNote(long id){
        return this.noteDAO.get(id);
    }*/

    public ArrayList<AppData> getAllNotes(){
        return this.noteDAO.getAll();
    }

}
