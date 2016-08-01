package com.jli.corkboard.model.repository;

import com.jli.corkboard.core.model.IPost;
import com.jli.corkboard.core.repository.Repository;
import com.jli.corkboard.core.repository.specification.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository implements Repository<IPost> {

    //TODO Use a database or something for persistence

    private Map<String, IPost> mData = new HashMap<>();

    @Override
    public void add(IPost obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public void remove(IPost obj) {
        mData.remove(obj.getId());
    }

    @Override
    public void update(IPost obj) {
        mData.put(obj.getId(), obj);
    }

    @Override
    public IPost get(String id) {
        return mData.get(id);
    }

    @Override
    public List query(Specification spec) {
        List results = new ArrayList();
        for(final IPost post : mData.values()) {
            if(spec.specified(post)) {
                results.add(post);
            }
        }
        return results;
    }
}