package com.jli.corkboard.core.repository;

import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.core.repository.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T obj);
    void remove(T obj);
    void update(T obj);

    T get(String id);

    List<T> query(Specification spec);
}
