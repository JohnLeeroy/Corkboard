package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Board extends ObservableBaseObject implements IBoard {

    String mBoardGroupId;
    List<IDeck> mDecks = new ArrayList<>();

    public Board(String id, String name) {
        super(id, name);
    }
    public Board(String id, Board board) { super(id, board.getName()); }

    protected Board(Parcel in) {
        super(in);
        mBoardGroupId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mBoardGroupId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    @Override
    public void addDeck(IDeck collection) {
        mDecks.add(collection);
//        setChanged();
//        notifyObservers();
    }

    public void getDeck(String name) {
        //
    }

    public List<IDeck> getDecks() {
        return mDecks;
    }

    @Override
    public String getBoardGroupId() {
        return mBoardGroupId;
    }

    @Override
    public void setBoardGroupId(String boardGroupId) {
        mBoardGroupId = boardGroupId;
    }
}
