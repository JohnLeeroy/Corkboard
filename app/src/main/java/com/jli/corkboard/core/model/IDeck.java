package com.jli.corkboard.core.model;

import android.os.Parcelable;

import com.jli.corkboard.model.Post;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IDeck extends IBaseObject, Parcelable {

    boolean addCard(Post post);

    List<Post> getPosts();

}
