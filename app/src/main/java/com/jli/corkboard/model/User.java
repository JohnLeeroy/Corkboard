package com.jli.corkboard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 6/5/16.
 */
public class User extends BaseObject {

    Map<String, Cluster> mClusterMap = new HashMap<>();

    public User(String id, String name) {
        super(id, name);
    }

    public User(String id, User user) {
        super(id, user.getName());
    }
    public Cluster addCluster(Cluster cluster) {
        return mClusterMap.put(cluster.getName(), cluster);
    }

    public Cluster getCluster(String name) {
        return  mClusterMap.get(name);
    }

    public List<Cluster> getClusters() {
        return new ArrayList<Cluster>(mClusterMap.values());
    }
}
