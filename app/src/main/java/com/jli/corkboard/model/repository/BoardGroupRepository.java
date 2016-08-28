package com.jli.corkboard.model.repository;

import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.core.repository.Repository;
import com.jli.corkboard.core.repository.specification.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardGroupRepository extends BaseRepository implements Repository<IBoardGroup> {

    //TODO Use a database or something for persistence

    private static Map<String, IBoardGroup> mData = new HashMap<>();

//    public BoardGroupRepository(IDataIO dataIO) {
//        mDataIO = dataIO;
//    }

    @Override
    public void add(IBoardGroup obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public void remove(IBoardGroup obj) {
        mData.remove(obj.getId());
    }

    @Override
    public void update(IBoardGroup obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public IBoardGroup get(String id) {
        return mData.get(id);
    }

    @Override
    public List<IBoardGroup> query(Specification spec) {
        List results = new ArrayList();
        for(final IBoardGroup group : mData.values()) {
            if(spec.specified(group)) {
                results.add(group);
            }
        }
        return results;
    }
}
