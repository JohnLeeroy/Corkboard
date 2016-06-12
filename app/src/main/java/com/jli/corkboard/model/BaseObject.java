package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 6/5/16.
 */
public class BaseObject implements Parcelable {
    protected String mId;
    protected String mName;

    public BaseObject(String id, String name) {
        mId = id;
        mName = name;
    }

    protected BaseObject(Parcel in) {
        mId = in.readString();
        mName = in.readString();
    }

    public static final Creator<BaseObject> CREATOR = new Creator<BaseObject>() {
        @Override
        public BaseObject createFromParcel(Parcel in) {
            return new BaseObject(in);
        }

        @Override
        public BaseObject[] newArray(int size) {
            return new BaseObject[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
    }
}
