package com.jli.corkboard.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jli.corkboard.R;

/**
 * Created by john on 6/12/16.
 */
public class PostCard extends CardView {

    TextView mTitleLabel;

    public PostCard(Context context) {
        super(context);
        bindView();
    }

    public PostCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();
    }

    public PostCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        View rootView = inflate(getContext(), R.layout.card_post, this);
        mTitleLabel = (TextView)rootView.findViewById(R.id.post_title);
    }

    public void setTitle(String title) {
        mTitleLabel.setText(title);
    }
}
