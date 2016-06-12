package com.jli.corkboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jli.corkboard.fragment.DeckFragment;
import com.jli.corkboard.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/12/16.
 */
public class DeckViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Deck> mDeck;

    public DeckViewPagerAdapter(FragmentManager fm, List<Deck> decks) {
        super(fm);
        mDeck = ((decks == null) ? new ArrayList<Deck>() : decks);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        if(mDeck.size() < position) {
            //Empty Add Deck fragment
            bundle.putParcelable("deck", mDeck.get(position));
        }
        bundle.putInt("position", position);
        return DeckFragment.newInstance(bundle);
    }

    @Override
    public int getCount() {
        return mDeck.size() + 1;
    }

    public void addDeck(Deck deck) {
        //TODO search for duplicate
        mDeck.add(deck);
    }
}
