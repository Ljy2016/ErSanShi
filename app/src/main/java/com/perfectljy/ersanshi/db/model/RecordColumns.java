package com.perfectljy.ersanshi.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.perfectljy.ersanshi.Common.Constants;

/**
 * Created by PerfectLjy on 2016/3/17.
 */
public class RecordColumns extends BaseColumns {
    public final static String TABLE_NAME = "record";
    public final static String TITLE = "title";
    public final static String DATE = "date";
    public final static String WEATHER = "weather";
    public final static String CONTENT = "content";
    public final static String IS_SECRET = "isSecret";
    public final static Uri CONTENT_URI=Uri.parse("content://"+ Constants.AUTHORITY+"/"+TABLE_NAME);
    public final static String CREAT_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement,"
            + TITLE + " text not null,"
            + DATE + " text not null,"
            + WEATHER + " text not null,"
            + CONTENT + " text not null,"
            + IS_SECRET + " INTEGER not null)";
    public static final String RECORD_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.ersanshi.record";
    public static final String RECORD_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.ersanshi.record";

}
