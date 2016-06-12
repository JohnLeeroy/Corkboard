package com.jli.corkboard.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jli.corkboard.Constant;
import com.jli.corkboard.R;
import com.jli.corkboard.model.Deck;
import com.jli.corkboard.view.AddDeckCardView;
import com.jli.corkboard.view.DeckBoardCardView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeckFragment extends Fragment {

    Deck mDeck;
    AddDeckCardView mAddDeckCard;
    DeckBoardCardView mDeckCard;

    public DeckFragment() {
        // Required empty public constructor
    }

    public static DeckFragment newInstance(Bundle deckBundle) {
        DeckFragment fragment = new DeckFragment();
        fragment.setArguments(deckBundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deck, container, false);
        bindUi(rootView);
        Bundle bundle = getArguments();
        if(bundle == null) {
            bundle = savedInstanceState;
        }
        mDeck = bundle.getParcelable("deck");

        bindUi(rootView);
        return rootView;
    }

    void bindUi(View rootView) {
        mAddDeckCard = (AddDeckCardView) rootView.findViewById(R.id.card_add_deck);
        mDeckCard = (DeckBoardCardView) rootView.findViewById(R.id.card_deck_board);
        refreshView();

        mAddDeckCard.setAddDeckListener(new AddDeckCardView.AddDeckCardListener() {
            @Override
            public void onAddDeck(Deck deck) {
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
                Intent createNewDeck = new Intent(Constant.ADD_NEW_DECK_BROADCAST);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.DECK_ARG, deck);
                createNewDeck.putExtras(bundle);
                localBroadcastManager.sendBroadcast(createNewDeck);

                mDeck = deck;
                refreshView();
            }
        });
    }

    void refreshView() {
        if (isDeckEmpty()) {
            mAddDeckCard.setVisibility(View.VISIBLE);
            mDeckCard.setVisibility(View.GONE);
        } else {
            mAddDeckCard.setVisibility(View.GONE);
            mDeckCard.setVisibility(View.VISIBLE);

        }
    }

    boolean isDeckEmpty() {
        return mDeck == null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constant.DECK_ARG, mDeck);
    }
}
