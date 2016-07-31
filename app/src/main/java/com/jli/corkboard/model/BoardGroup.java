package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IBoardGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class BoardGroup implements IBoardGroup, Parcelable {

    private String name;

    private String id;

    List<IBoard> mBoards = new ArrayList<>();

    public BoardGroup() { }
    public BoardGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public BoardGroup(String id, BoardGroup boardGroup) {
        this(id, boardGroup.getName());
    }

    @Override
    public void addBoard(IBoard board) {
        mBoards.add(board);
    }

    public List<IBoard> getBoards() { return mBoards; }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
