package com.perfectljy.ersanshi.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.perfectljy.ersanshi.Utils.DataProviderHelper;

/**
 * Created by PerfectLjy on 2016/3/17.
 */
public class RecordModel extends BaseModel {
    private String id;
    private String title;
    private String date;
    private String weather;
    private String content;
    private int isSecart=0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsSecart() {
        return isSecart;
    }

    public void setIsSecart(int isSecart) {
        this.isSecart = isSecart;
    }

    public RecordModel() {
    }

    public RecordModel(Parcel in) {
        readBase(in);
        title = in.readString();
        date = in.readString();
        weather = in.readString();
        content = in.readString();
        isSecart = in.readInt();
    }

    @Override
    public ContentValues values() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecordColumns.TITLE, title);
        contentValues.put(RecordColumns._ID, id);
        contentValues.put(RecordColumns.DATE, date);
        contentValues.put(RecordColumns.WEATHER, weather);
        contentValues.put(RecordColumns.CONTENT, content);
        contentValues.put(RecordColumns.IS_SECRET, isSecart);

        return contentValues;
    }

    @Override
    public Uri getContentUri() {
        return RecordColumns.CONTENT_URI;
    }

    @Override
    public String getTable() {
        return RecordColumns.TABLE_NAME;
    }

    @Override
    public RecordModel getModel(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        RecordModel recordModel = new RecordModel();
        recordModel.id = DataProviderHelper.parseString(cursor, BaseColumns._ID);
        //recordModel.recordId =  DataProviderHelper.parseString(cursor,RecordColumns.RECORDID);
        recordModel.title = DataProviderHelper.parseString(cursor, RecordColumns.TITLE);
        recordModel.date = DataProviderHelper.parseString(cursor, RecordColumns.DATE);
        recordModel.weather = DataProviderHelper.parseString(cursor, RecordColumns.WEATHER);
        recordModel.content = DataProviderHelper.parseString(cursor, RecordColumns.CONTENT);
        recordModel.isSecart = DataProviderHelper.parseInt(cursor, RecordColumns.IS_SECRET);

        return recordModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeBase(dest, flags);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(weather);
        dest.writeString(content);
        dest.writeInt(isSecart);
    }

    public static final Parcelable.Creator<RecordModel> CREATOR = new Creator() {

        @Override
        public RecordModel createFromParcel(Parcel source) {
            RecordModel p = new RecordModel(source);
            return p;
        }

        @Override
        public RecordModel[] newArray(int size) {
            return new RecordModel[size];
        }
    };
}
