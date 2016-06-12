package com.jli.corkboard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Cluster extends BaseObject{

    List<Board> mBoards = new ArrayList<>();

    public Cluster(String id, String name) {
        super(id, name);
    }

    public void addBoard(Board board) {
        mBoards.add(board);
    }

    public List<Board> getBoards() { return mBoards; }
}
