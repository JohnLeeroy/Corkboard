package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IBaseObject;

import java.util.Observable;


/**
 * Created by john on 6/5/16.
 */
public class ObservableBaseObject extends Observable implements IBaseObject, Parcelable {

    protected String mId;
    protected String mName;

    protected ObservableBaseObject(Parcel in) {
        mId = in.readString();
        mName = in.readString();
    }

    public ObservableBaseObject(String id, String name) {
        mId = id;
        mName = name;
    }
    public ObservableBaseObject() { }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    @Override
    public void setName(String name) {
        mName = name;
        setChanged();
        notifyObservers(mName);
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
