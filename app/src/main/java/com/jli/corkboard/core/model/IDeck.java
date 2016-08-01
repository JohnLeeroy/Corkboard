package com.jli.corkboard.core.model;

import com.jli.corkboard.model.Post;

import java.util.List;

/**
 * Created by john on 7/31/16.
 */
public interface IDeck extends IBaseObject {

    boolean addCard(Post post);

    List<Post> getPosts();

}
