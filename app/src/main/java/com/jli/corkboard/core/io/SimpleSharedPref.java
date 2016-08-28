package com.jli.corkboard.core.io;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleSharedPref<T> implements GenericDataIO<T> {

    SharedPreferences mPreferences;

    public SimpleSharedPref(Context context, String key) {
        mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(String filePath, T[] collection) {
        Gson gson = new GsonBuilder().create();
        String data = gson.toJson(collection);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(filePath, data);
        editor.apply();
        return true;
    }

    @Override
    public T[] load(String s, Class clazz) {
        String data = mPreferences.getString(s, "");
        Gson gson = new GsonBuilder().create();
        T[] result = (T[]) gson.fromJson(data, clazz);
        return result;
    }
}
