package com.anjani.mad.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Anjani Reddy on 22-10-2017.
 */

public class AppTable {
    static final String TABLENAME = "Filter";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_IMAGE = "thumb_url";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+"(");
        sb.append(COLUMN_NAME+ " text not null,");
        sb.append(COLUMN_PRICE+ " text not null,");
        sb.append(COLUMN_IMAGE+ " text );");
try {
    db.execSQL(sb.toString());
}catch (SQLException e)
{
    e.printStackTrace();
}

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        AppTable.onCreate(db);
    }
}
