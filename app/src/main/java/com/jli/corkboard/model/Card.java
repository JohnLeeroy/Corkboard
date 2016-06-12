package com.jli.corkboard.model;

import android.os.Parcel;

/**
 * Created by john on 6/5/16.
 */
public class Card extends BaseObject {

    public Card(String id, String name) {
        super(id, name);
    }

    protected Card(Parcel in) {
        super(in);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
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

}
