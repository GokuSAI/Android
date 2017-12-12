package com.anjani.mad.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Anjani Reddy on 22-10-2017.
 */

public class AppDAO {
    public SQLiteDatabase db;

    public AppDAO(SQLiteDatabase db) {
        this.db = db;
    }


    public long save(AppData note)
    {
        ContentValues values = new ContentValues();
        values.put(AppTable.COLUMN_NAME,note.getAppName());
        values.put(AppTable.COLUMN_PRICE,note.getAppPrice());
        values.put(AppTable.COLUMN_IMAGE,note.getLimage());
        return db.insert(AppTable.TABLENAME,null,values);
    }

    /*public boolean update(AppData note)
    {
        ContentValues values = new ContentValues();
        values.put(AppTable.COLUMN_NAME,note.getAppName());
        values.put(AppTable.COLUMN_PRICE,note.getAppPrice());
        values.put(AppTable.COLUMN_IMAGE,note.getImage());
        return db.update(AppTable.TABLENAME,values, AppTable.COLUMN_ID+"=?",new String[]{note.get_id()+""})>0;
    }*/

    public boolean delete(AppData note)
    {
        return db.delete(AppTable.TABLENAME, AppTable.COLUMN_NAME+"=?",new String[]{note.getAppName()+""})>0;
    }

    /*public AppData get(long id)
    {
        Note note = null;
        Cursor c = db.query(true, AppTable.TABLENAME,new String[]{
                AppTable.COLUMN_ID, AppTable.COLUMN_SUBJECT, AppTable.COLUMN_TEXT},
                AppTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);

        if(c!= null && c.moveToFirst())
        {
            note = buildNoteFromCursor(c);

            if(c.isClosed())
            {
                c.close();
            }


        }
        return note;
    }*/

    public ArrayList<AppData> getAll()
    {
        AppData note = null;
        ArrayList<AppData> listNotes = new ArrayList<>();

        Cursor c = db.query(AppTable.TABLENAME,new String[]{
                        AppTable.COLUMN_NAME, AppTable.COLUMN_PRICE, AppTable.COLUMN_IMAGE}, null,null,null,null,null);

        if(c!= null && c.moveToFirst())
        {
            do {
                note = buildNoteFromCursor(c);
                if(note != null)
                    listNotes.add(note);
            }while (c.moveToNext());


            if (c.isClosed()) {
                c.close();
            }
        }
        return listNotes;
    }

    public AppData buildNoteFromCursor(Cursor c)
    {
        AppData note = null;
        if(c != null)
        {

            note = new AppData();

            note.setAppName(c.getString(0));
            note.setAppPrice(c.getString(1));
            note.setImage(c.getString(2));

        }

        return note;
    }

}
