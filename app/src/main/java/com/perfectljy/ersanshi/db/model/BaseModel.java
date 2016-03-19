package com.perfectljy.ersanshi.db.model;

import android.content.ContentValues;
import android.os.Parcel;

/**
 * Created by PerfectLjy on 2016/3/14.
 */
public abstract class BaseModel implements IModel {
    protected String id;

    protected void readBase(Parcel in) {
        id = in.readString();
    }

    protected void writeBase(Parcel dest, int flags) {
        dest.writeString(id);
    }

    protected ContentValues contentValues() {
        ContentValues cv = new ContentValues();
        cv.put(BaseColumns._ID, id);
        return cv;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


}
