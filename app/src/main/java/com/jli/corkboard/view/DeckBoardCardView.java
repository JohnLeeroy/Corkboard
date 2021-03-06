package com.jli.corkboard.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.jli.corkboard.R;

/**
 * Created by john on 6/12/16.
 */
public class DeckBoardCardView extends CardView {

    EditText mTitle;

    public DeckBoardCardView(Context context) {
        super(context);
        bindView();
    }

    public DeckBoardCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();
    }

    public DeckBoardCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        View rootView = inflate(getContext(), R.layout.card_deck_board, this);
        mTitle = (EditText) findViewById(R.id.deck_board_title);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

}
