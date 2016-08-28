package com.jli.corkboard.core.io;

public interface GenericDataIO<T> {

    boolean save(String filePath, T[] collection);

    T[] load(String s, Class<T[]> clazz);
}
