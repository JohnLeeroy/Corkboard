package com.jli.corkboard.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jli.corkboard.R;
import com.jli.corkboard.model.Deck;

import java.util.UUID;

/**
 * Created by john on 6/5/16.
 */
public class AddDeckCardView extends CardView {

    TextView mLabel;
    EditText mCollectionName;
    AddDeckCardListener mAddDeckCardListener;

    public interface AddDeckCardListener {
        void onAddDeck(Deck deck);
    }

    public AddDeckCardView(Context context) {
        super(context);
        bindView();
    }

    public AddDeckCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();
    }

    public AddDeckCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        inflate(getContext(), R.layout.card_add_deck, this);
        mLabel = (TextView) findViewById(R.id.add_collection_field);
        mCollectionName = (EditText) findViewById(R.id.edit_collection_name);


        mLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLabel.setVisibility(GONE);
                mCollectionName.setVisibility(VISIBLE);
                mCollectionName.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mCollectionName, InputMethodManager.SHOW_IMPLICIT);
                mCollectionName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if(mAddDeckCardListener != null) {
                                String deckName = mCollectionName.getText().toString();
                                Deck deck = new Deck(UUID.randomUUID().toString(), deckName);
                                mAddDeckCardListener.onAddDeck(deck);
                                mLabel.clearFocus();
                                imm.hideSoftInputFromWindow(mLabel.getWindowToken(), 0);
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    mLabel.setVisibility(INVISIBLE);
                    mCollectionName.setVisibility(GONE);
                }
            }
        });
    }

    public void setAddDeckListener(AddDeckCardListener listener) {
        mAddDeckCardListener = listener;
    }
}
