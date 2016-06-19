package com.jli.corkboard.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jli.corkboard.R;
import com.jli.corkboard.model.Post;

import java.util.UUID;

/**
 * Created by john on 6/12/16.
 */
public class AddPostCardView extends CardView {

    EditText mEditTitle;
    Button mAddPostBtn;
    AddPostCardListener mAddPostCardListener;

    public interface AddPostCardListener {
        void onAddCard(Post post);
    }

    public AddPostCardView(Context context) {
        super(context);
        bindView();
    }

    public AddPostCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindView();
    }

    public AddPostCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView();
    }

    private void bindView() {
        inflate(getContext(), R.layout.card_add_post, this);
        mEditTitle = (EditText) findViewById(R.id.card_post_title);
        mAddPostBtn = (Button) findViewById(R.id.card_add_post_btn);

        mAddPostBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddPostCardListener != null) {
                    String title = mEditTitle.getText().toString();
                    Post post = new Post(UUID.randomUUID().toString(), title);
                    mAddPostCardListener.onAddCard(post);
                }
            }
        });
    }

    public void setAddPostCardListener(AddPostCardListener listener) {
        mAddPostCardListener = listener;
    }
}
