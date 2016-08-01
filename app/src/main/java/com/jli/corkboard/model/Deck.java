package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Deck extends ObservableBaseObject implements IDeck {

    List<Post> mPosts = new ArrayList<>();

    public Deck(String id, String name, List<Post> posts) {
        super(id, name);
        mPosts = posts;
    }

    public boolean addCard(Post post) {
        boolean result = mPosts.add(post);
        if (result) {
            setChanged();
            notifyObservers();
        }
        return result;
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
