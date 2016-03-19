package com.perfectljy.ersanshi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.perfectljy.ersanshi.db.model.RecordColumns;
import com.perfectljy.ersanshi.db.model.RecordModel;
import com.perfectljy.ersanshi.db.model.SafeColimns;

/**
 * Created by PerfectLjy on 2016/3/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ersanshi.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SafeColimns.CREAT_TABLE);
        db.execSQL(RecordColumns.CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SafeColimns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecordColumns.CREAT_TABLE);
        onCreate(db);
    }
}
