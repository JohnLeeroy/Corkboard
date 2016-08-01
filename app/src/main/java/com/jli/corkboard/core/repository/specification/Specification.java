package com.jli.corkboard.core.repository.specification;

public interface Specification<T> {

    boolean specified(T object);
}
