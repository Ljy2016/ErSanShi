package com.perfectljy.ersanshi.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.perfectljy.ersanshi.Utils.DataProviderHelper;

/**
 * Created by PerfectLjy on 2016/3/14.
 */
public class SafeModel extends BaseModel {
    private String safeId;
    private String password;

    public String getSafeId() {
        return safeId;
    }

    public void setSafeId(String safeId) {
        this.safeId = safeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String question;
    private String answer;

    public SafeModel() {
    }

    public SafeModel(Parcel in) {
        readBase(in);
        safeId = in.readString();
        password = in.readString();
        question = in.readString();
        answer = in.readString();
    }

    @Override
    public ContentValues values() {
        ContentValues cv = contentValues();
        cv.put(SafeColimns.USER_PSAAWORD, password);
        cv.put(SafeColimns.SAFE_QUESTION, question);
        cv.put(SafeColimns.SAFE_QUESTION_ANSWER, answer);
        return cv;
    }

    @Override
    public Uri getContentUri() {
        return SafeColimns.CONTENT_URI;
    }

    @Override
    public String getTable() {
        return SafeColimns.TABLE_NAME;
    }

    @Override
    public SafeModel getModel(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        SafeModel safeModel = new SafeModel();
        safeModel.id = DataProviderHelper.parseString(cursor, BaseColumns._ID);
        safeModel.password = DataProviderHelper.parseString(cursor, SafeColimns.USER_PSAAWORD);
        safeModel.question = DataProviderHelper.parseString(cursor, SafeColimns.SAFE_QUESTION);
        safeModel.answer = DataProviderHelper.parseString(cursor, SafeColimns.SAFE_QUESTION_ANSWER);
        return safeModel;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         writeBase(dest,flags);
        dest.writeString(password);
        dest.writeString(question);
        dest.writeString(answer);
    }
    public static  final Parcelable.Creator<SafeModel> CREATOR=new Creator<SafeModel>() {
        @Override
        public SafeModel createFromParcel(Parcel source) {
            SafeModel safeModel=new SafeModel(source);
            return safeModel;
        }

        @Override
        public SafeModel[] newArray(int size) {
            return new SafeModel[size];
        }
    };

}
