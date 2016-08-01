package com.jli.corkboard.model;

import android.os.Parcel;

import com.jli.corkboard.core.model.IPost;

/**
 * Created by john on 6/5/16.
 */
public class Post extends ObservableBaseObject implements IPost {

    public Post(String id, String name) {
        super(id, name);
    }

}
