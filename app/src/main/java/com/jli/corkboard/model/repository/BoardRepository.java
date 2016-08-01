package com.jli.corkboard.model.repository;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.repository.Repository;
import com.jli.corkboard.core.repository.specification.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepository implements Repository<IBoard> {

    //TODO Use a database or something for persistence

    private Map<String, IBoard> mData = new HashMap<>();

    @Override
    public void add(IBoard obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public void remove(IBoard obj) {
        mData.remove(obj.getId());
    }

    @Override
    public void update(IBoard obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public IBoard get(String id) {
        return mData.get(id);
    }

    @Override
    public List query(Specification spec) {
        List results = new ArrayList();
        for(final IBoard board : mData.values()) {
            if(spec.specified(board)) {
                results.add(board);
            }
        }
        return results;
    }
}
