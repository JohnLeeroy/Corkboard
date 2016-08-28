package com.jli.corkboard.core.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jli.corkboard.core.io.GenericDataIO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonGenericIO<T> implements GenericDataIO<T> {
    @Override
    public boolean save(String filePath, T[] collection) {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(collection, writer);
        return true;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
    }

    @Override
    public T[] load(String s, Class<T[]> clazz) {
        try(Reader reader = new FileReader(s)) {
            Gson gson = new GsonBuilder().create();
            T[] result = gson.fromJson(reader, clazz);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}