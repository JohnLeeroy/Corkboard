package com.jli.corkboard.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jli.corkboard.Constant;
import com.jli.corkboard.R;
import com.jli.corkboard.model.Post;
import com.jli.corkboard.model.Deck;
import com.jli.corkboard.view.AddDeckCardView;
import com.jli.corkboard.view.AddPostCardView;
import com.jli.corkboard.view.DeckBoardCardView;
import com.jli.corkboard.view.adapter.PostRecyclerAdapter;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeckFragment extends Fragment {

    Deck mDeck;
    AddDeckCardView mAddDeckCard;
    DeckBoardCardView mDeckCard;
    AddPostCardView mAddPostCard;
    Button mAddPostBtn;

    RecyclerView mPostRecyclerView;
    PostRecyclerAdapter mAdapter;


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
        Bundle bundle = savedInstanceState;
        if (bundle == null) {
            bundle = getArguments();
        }
        mDeck = bundle.getParcelable(Constant.DECK_ARG);

        bindUi(rootView);
        return rootView;
    }

    void bindUi(View rootView) {
        mAddDeckCard = (AddDeckCardView) rootView.findViewById(R.id.card_add_deck);
        mDeckCard = (DeckBoardCardView) rootView.findViewById(R.id.card_deck_board);
        mAddPostCard = (AddPostCardView) rootView.findViewById(R.id.card_add_post);
        mAddPostBtn = (Button) rootView.findViewById(R.id.add_post_btn);

        mPostRecyclerView = (RecyclerView) rootView.findViewById(R.id.post_recycler_view);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mDeck != null) {
            mAdapter = new PostRecyclerAdapter(mDeck.getPosts());
        } else {
            mAdapter = new PostRecyclerAdapter();
        }

        mPostRecyclerView.setAdapter(mAdapter);

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
                mAdapter.setData(mDeck.getPosts());
                refreshView();
            }
        });

        mAddPostCard.setAddPostCardListener(new AddPostCardView.AddPostCardListener() {
            @Override
            public void onAddCard(Post post) {
                addPost(post);
                switchToAddCardButton();
            }
        });

        mAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddPostBtn.setVisibility(View.GONE);
                mAddPostCard.setVisibility(View.VISIBLE);

                final EditText title = (EditText) mAddPostCard.findViewById(R.id.card_post_title);
                title.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(title, InputMethodManager.SHOW_IMPLICIT);
                title.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            String postName = title.getText().toString();
                            Post post = new Post(UUID.randomUUID().toString(), postName);
                            addPost(post);
                            switchToAddCardButton();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void addPost(Post post) {
        mDeck.addCard(post);
        mAdapter.notifyDataSetChanged();
    }

    private void switchToAddCardButton() {
        final EditText title = (EditText) mAddPostCard.findViewById(R.id.card_post_title);
        title.setText("");
        mPostRecyclerView.requestFocus(); //clear focus from edittext
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
        mAddPostBtn.setVisibility(View.VISIBLE);
        mAddPostCard.setVisibility(View.GONE);
    }

    void refreshView() {
        if (isDeckEmpty()) {
            mAddDeckCard.setVisibility(View.VISIBLE);
            mDeckCard.setVisibility(View.GONE);
        } else {
            mAddDeckCard.setVisibility(View.GONE);
            mDeckCard.setVisibility(View.VISIBLE);
            mDeckCard.setTitle(mDeck.getName());
        }
    }

    boolean isDeckEmpty() {
        return mDeck == null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(getArguments());
        outState.putParcelable(Constant.DECK_ARG, mDeck);
    }
}
