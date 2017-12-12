package com.anjani.mad.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anjani Reddy on 22-10-2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "appdata.db";
    static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        AppTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        AppTable.onUpgrade(sqLiteDatabase,i,i1);

    }
}
