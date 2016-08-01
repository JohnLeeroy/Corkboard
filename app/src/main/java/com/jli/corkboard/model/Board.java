package com.jli.corkboard.model;

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

    @Override
    public void addDeck(IDeck collection) {
        mDecks.add(collection);
        setChanged();
        notifyObservers();
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
