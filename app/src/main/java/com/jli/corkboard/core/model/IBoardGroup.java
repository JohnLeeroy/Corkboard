package com.jli.corkboard.core.model;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IBoardGroup extends IBaseObject {
    void addBoard(IBoard board);
    List<IBoard> getBoards();
}
