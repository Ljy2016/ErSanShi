package com.perfectljy.ersanshi.db.model;

import android.content.ContentResolver;
import android.net.Uri;

import com.perfectljy.ersanshi.Common.Constants;

/**
 * Created by PerfectLjy on 2016/3/14.
 */
public class SafeColimns extends BaseColumns {
    public final static String TABLE_NAME = "safe";
    public final static String USER_PSAAWORD = "password";
    public final static String SAFE_QUESTION = "question";
    public final static String SAFE_QUESTION_ANSWER = "answer";
    public final static Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + TABLE_NAME);
    public final static String CREAT_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement,"
            + USER_PSAAWORD + " text not null,"
            + SAFE_QUESTION + " text not null,"
            + SAFE_QUESTION_ANSWER + " text not null);";
    public static final String SAFE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.ersanshi.safe";
    public static final String SAFE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.ersanshi.safe";
}
