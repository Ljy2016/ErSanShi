package com.perfectljy.ersanshi.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PerfectLjy on 2016/3/14.
 */
public interface IModel extends Parcelable {
  public abstract ContentValues values();
    public abstract Uri getContentUri();
    public abstract String getTable();
    public abstract <T extends BaseModel> T getModel(Cursor cursor);
}
