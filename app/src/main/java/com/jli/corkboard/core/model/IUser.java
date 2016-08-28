package com.jli.corkboard.core.model;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IUser extends IBaseObject, Parcelable {

    IBoardGroup addBoardGroup(IBoardGroup boardGroup);
    IBoardGroup getCluster(String name);
    List<IBoardGroup> getBoardGroups();

}
