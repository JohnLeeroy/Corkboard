package com.jli.corkboard.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Board extends BaseObject {

    List<CardCollection> mCardCollections = new ArrayList<>();

    public Board(String id, String name) {
        super(id, name);
    }

    protected Board(Parcel in) {
        super(in);
    }

    public static final Creator<BaseObject> CREATOR = new Creator<BaseObject>() {
        @Override
        public BaseObject createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public BaseObject[] newArray(int size) {
            return new BaseObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    void addCardCollection(CardCollection collection) {
        mCardCollections.add(collection);
    }

    void getCardCollection(String name) {
        //
    }

    List<CardCollection> getCardCollections() {
        return mCardCollections;
    }
}
