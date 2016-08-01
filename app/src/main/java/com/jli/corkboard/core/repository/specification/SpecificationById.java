package com.jli.corkboard.core.repository.specification;

import com.jli.corkboard.core.model.IBaseObject;

public class SpecificationById<T extends IBaseObject> implements Specification<T> {

    String mId;

    public SpecificationById(String id) {
        mId = id;
    }

    @Override
    public boolean specified(T object) {
        return object.getId().equals(mId);
    }
}
