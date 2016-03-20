package com.perfectljy.ersanshi.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.perfectljy.ersanshi.Common.Constants;
import com.perfectljy.ersanshi.Utils.LogUtil;
import com.perfectljy.ersanshi.db.model.RecordColumns;
import com.perfectljy.ersanshi.db.model.SafeColimns;

import java.util.List;

/**
 * Created by PerfectLjy on 2016/3/15.
 */
public class DateProvider extends ContentProvider implements IDataProvider {

    private SQLiteHelper dbHelper;
    private static final UriMatcher mUriMatcher;
    private static final int SAFE_CHECK = 1;
    private static final int REACORD = 2;
    private static final String TAG = "DateProvider";

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(Constants.AUTHORITY, SafeColimns.TABLE_NAME, SAFE_CHECK);
        mUriMatcher.addURI(Constants.AUTHORITY, RecordColumns.TABLE_NAME, REACORD);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new SQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case SAFE_CHECK:
                return queryAllList(uri, projection, selection, selectionArgs, sortOrder);

            case REACORD:
                return queryAllList(uri, projection, selection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("unknow uri:" + uri);

        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (mUriMatcher.match(uri)) {
            case SAFE_CHECK:
                insertItemByUri(uri, values);
                return uri;
            case REACORD:
                insertItemByUri(uri, values);
                return uri;
            default:
                throw new IllegalArgumentException(" Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (mUriMatcher.match(uri)) {
            case SAFE_CHECK:
                return deleteItemByCondition(uri, selection, selectionArgs);
            case REACORD:
                return deleteItemByCondition(uri,selection,selectionArgs);
            default:
                throw new IllegalArgumentException(" Unknown URI: " + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (mUriMatcher.match(uri)) {
            case SAFE_CHECK:
                return updateItemByCondition(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(" Unknown URI: " + uri);
        }

    }

    @Override
    public Cursor queryAllList(Uri uri, String[] columns, String where, String[] whereArgs, String orderBy) {
        String tableName = uri.getPathSegments().get(0);

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(tableName, null, where, whereArgs, null, null, orderBy);
        return queryWithNotify(uri, cursor);

    }

    @Override
    public Cursor queryItemById(Uri uri) {
        return null;
    }

    @Override
    public void insertItemByUri(Uri uri, ContentValues values) {
        if (values == null) {
            LogUtil.e(TAG, "出现错误：插入的值为空。");
            return;
        }
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String tableName = uri.getPathSegments().get(0);
        long rowId = sqLiteDatabase.insert(tableName, null, values);//插入的行数
        getContext().getContentResolver().notifyChange(uri, null);
        if (rowId > 0) {
            Uri resultUri = ContentUris.withAppendedId(uri, rowId);
            LogUtil.d(TAG, "insert resultUri" + resultUri);
        }
    }

    @Override
    public int deleteItemByCondition(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        try {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            String tableName = uri.getPathSegments().get(0);
            count = sqLiteDatabase.delete(tableName, selection, selectionArgs);
        } catch (SQLiteException e) {
            new Exception("" + e.getMessage());
        }

        return count;
    }

    @Override
    public int deleteItemByUri(Uri uri) {
        return 0;
    }

    @Override
    public int updateItemByCondition(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        List<String> path = uri.getPathSegments();
        String table = path.get(0);
        return dbHelper.getWritableDatabase().update(table, values, selection,
                selectionArgs);
    }

    @Override
    public int updateItmeByUri(Uri uri, ContentValues values) {
        return 0;
    }

    private Cursor queryWithNotify(Uri uri, Cursor cursor) {
        if (cursor == null) {
            LogUtil.d(TAG, "cursor is null");
        } else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

}
