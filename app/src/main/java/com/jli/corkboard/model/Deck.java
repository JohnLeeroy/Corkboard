package com.jli.corkboard.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by john on 6/5/16.
 */
public class Deck extends BaseObject {

    List<Post> mPosts = new ArrayList<>();

    public Deck(String id, String name) {
        super(id, name);
    }

    public Deck(String id, String name, List<Post> posts) {
        super(id, name);
        mPosts = posts;
    }

    public boolean addCard(Post post) {
        return mPosts.add(post);
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
