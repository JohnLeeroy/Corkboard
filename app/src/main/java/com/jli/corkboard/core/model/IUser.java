package com.jli.corkboard.core.model;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IUser extends IBaseObject {

    IBoardGroup addBoardGroup(IBoardGroup boardGroup);
    IBoardGroup getCluster(String name);
    List<IBoardGroup> getBoardGroups();

}
