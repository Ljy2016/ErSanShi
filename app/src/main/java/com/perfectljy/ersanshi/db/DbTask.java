package com.perfectljy.ersanshi.db;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.perfectljy.ersanshi.db.model.RecordColumns;
import com.perfectljy.ersanshi.db.model.RecordModel;

/**
 * Created by PerfectLjy on 2016/3/20.
 * 后台完成数据库中数据的写入删除
 */
public class DbTask extends AsyncTask<String, Integer, byte[]> {


    public enum dbType {ADD, DELETE}

    private dbType type;
    ContentResolver contentResolver;
    private Context context;

    public DbTask(Context context, dbType type) {
        contentResolver = context.getContentResolver();
        this.type = type;

    }

    @Override
    protected byte[] doInBackground(String... params) {
        switch (type) {
            case ADD:
                break;
            case DELETE:
                contentResolver.delete(RecordColumns.CONTENT_URI, "_id=?", params);
                break;
        }
        return null;
    }

}
