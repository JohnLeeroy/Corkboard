package com.jli.corkboard.core.model;

import android.os.Parcelable;

import com.jli.corkboard.model.Board;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IBoardGroup<T extends IBoard> extends IBaseObject, Parcelable {
    void addBoard(T board);
   List<T> getBoards();
}
