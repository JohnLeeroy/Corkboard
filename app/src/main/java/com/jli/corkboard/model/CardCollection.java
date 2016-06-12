package com.jli.corkboard.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by john on 6/5/16.
 */
public class CardCollection extends BaseObject {

    Set<Card> mCards = new HashSet<>();

    public CardCollection(String id, String name) {
        super(id, name);
    }

}
