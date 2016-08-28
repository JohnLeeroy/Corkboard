package com.jli.corkboard.core.model;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IBoard extends IBaseObject, Parcelable {

    void addDeck(IDeck collection);

    void getDeck(String name);

    List<IDeck> getDecks();

    String getBoardGroupId();

    void setBoardGroupId(String boardGroupId);
}
