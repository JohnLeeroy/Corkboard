package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jli.corkboard.core.model.IBaseObject;

import java.util.Observable;

/**
 * Created by john on 6/5/16.
 */
public class ObservableBaseObject extends Observable implements IBaseObject {

    protected String mId;

    protected String mName;

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

}
