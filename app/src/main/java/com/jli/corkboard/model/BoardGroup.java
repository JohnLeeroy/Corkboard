package com.jli.corkboard.model;

import android.os.Parcel;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IBoardGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class BoardGroup<T extends IBoard> extends ObservableBaseObject implements IBoardGroup<T>, Serializable {

    List<T> mBoards = new ArrayList<>();

    public BoardGroup(String id, String name) {
        super(id, name);
    }

    protected BoardGroup(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BoardGroup> CREATOR = new Creator<BoardGroup>() {
        @Override
        public BoardGroup createFromParcel(Parcel in) {
            return new BoardGroup(in);
        }

        @Override
        public BoardGroup[] newArray(int size) {
            return new BoardGroup[size];
        }
    };

    @Override
    public void addBoard(T board) {
        mBoards.add(board);
    }

    @Override
    public List<T> getBoards() {
        return mBoards;
    }
}
