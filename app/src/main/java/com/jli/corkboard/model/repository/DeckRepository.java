package com.jli.corkboard.model.repository;

import com.jli.corkboard.core.model.IDeck;
import com.jli.corkboard.core.repository.Repository;
import com.jli.corkboard.core.repository.specification.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckRepository implements Repository<IDeck> {

    //TODO Use a database or something for persistence

    private Map<String, IDeck> mData = new HashMap<>();

    @Override
    public void add(IDeck obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public void remove(IDeck obj) {
        mData.remove(obj.getId());
    }

    @Override
    public void update(IDeck obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public IDeck get(String id) {
        return mData.get(id);
    }

    @Override
    public List query(Specification spec) {
        List results = new ArrayList();
        for(final IDeck deck : mData.values()) {
            if(spec.specified(deck)) {
                results.add(deck);
            }
        }
        return results;
    }
}