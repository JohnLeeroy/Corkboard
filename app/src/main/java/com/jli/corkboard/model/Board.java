package com.jli.corkboard.model;

import android.os.Parcel;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Board extends BaseObject implements IBoard {

    List<IDeck> mDecks = new ArrayList<>();

    public Board(String id, String name) {
        super(id, name);
    }
    public Board(String id, Board board) { super(id, board.getName()); }

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

    @Override
    public void addDeck(IDeck collection) {
        mDecks.add(collection);
    }

    public void getDeck(String name) {
        //
    }

    public List<IDeck> getDecks() {
        return mDecks;
    }
}
