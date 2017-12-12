package edu.uncc.notekeeper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sai Yesaswy on 2/26/2017.
 */

public class NotesTable {

    static final String TABLENAME = "notes";
    static final String COLUMN_ID = "id";
    static final String COLUMN_TASKNOTE = "note";
    static final String COLUMN_STATUS = "status";
    static final String COLUMN_PRIORITY = "priority";
    static final String COLUMN_UPDATETIME = "update_time";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " +TABLENAME+" (");
        sb.append(COLUMN_ID+ " integer primary key autoincrement, ");
        sb.append(COLUMN_TASKNOTE+ " text not null, ");
        sb.append(COLUMN_STATUS+ " text not null, ");
        sb.append(COLUMN_PRIORITY+ " text not null, ");
        sb.append(COLUMN_UPDATETIME+ " text not null);");

        db.execSQL(sb.toString());
    }

    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        NotesTable.onCreate(db);
    }



}
