package com.jli.corkboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jli.corkboard.model.Card;

import java.util.UUID;

/**
 * Created by john on 6/5/16.
 */
public class AddCollectionCardView extends CardView {

    TextView mLabel;
    EditText mCollectionName;

    public AddCollectionCardView(Context context) {
        super(context);
        bindView();
    }

    public AddCollectionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();
    }

    public AddCollectionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        inflate(getContext(), R.layout.card_add_list, this);
        mLabel = (TextView) findViewById(R.id.add_collection_field);
        mCollectionName = (EditText) findViewById(R.id.edit_collection_name);

        mLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLabel.setVisibility(GONE);
                mCollectionName.setVisibility(VISIBLE);
                mCollectionName.requestFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mCollectionName, InputMethodManager.SHOW_IMPLICIT);
                mCollectionName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Card card = new Card(UUID.randomUUID().toString(), mCollectionName.getText().toString());
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
                            Intent createNewCard = new Intent("Blah");
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("Card", card);
                            createNewCard.putExtras(bundle);
                            localBroadcastManager.sendBroadcast(createNewCard);
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
}
