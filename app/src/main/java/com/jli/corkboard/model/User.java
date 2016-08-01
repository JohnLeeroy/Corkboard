package com.jli.corkboard.model;

import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.core.model.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 6/5/16.
 */
public class User implements IUser {

    private String name;
    private String id;

    Map<String, IBoardGroup> mClusterMap = new HashMap<>();

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, User user) {
        this(id, user.getName());
    }

    public IBoardGroup addBoardGroup(IBoardGroup boardGroup) {
        return mClusterMap.put(boardGroup.getName(), boardGroup);
    }

    public IBoardGroup getCluster(String name) {
        return  mClusterMap.get(name);
    }

    public List<IBoardGroup> getBoardGroups() {
        return new ArrayList<IBoardGroup>(mClusterMap.values());
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
