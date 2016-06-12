package com.jli.corkboard.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by john on 6/5/16.
 */
public class Deck extends BaseObject {

    Set<Card> mCards = new HashSet<>();

    public Deck(String id, String name) {
        super(id, name);
    }
    public Deck(String id, String name, Set<Card> cards) {
        super(id, name);
        mCards = cards;
    }

    public boolean addCard(Card card) {
        return mCards.add(card);
    }

}
