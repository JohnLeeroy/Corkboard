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
public class BoardGroup extends ObservableBaseObject implements IBoardGroup {

    List<IBoard> mBoards = new ArrayList<>();

    @Override
    public void addBoard(IBoard board) {
        mBoards.add(board);
    }

    public List<IBoard> getBoards() { return mBoards; }

}
