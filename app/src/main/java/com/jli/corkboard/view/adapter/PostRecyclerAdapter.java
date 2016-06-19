package com.jli.corkboard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jli.corkboard.R;
import com.jli.corkboard.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/12/16.
 */
public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>  {

    List<Post> mPosts = new ArrayList<>();

    public PostRecyclerAdapter() {
        super();
    }

    public PostRecyclerAdapter(List<Post> posts) {
        super();
        mPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_post, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.setTitle(post.getName());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void setData(List<Post> posts) {
        mPosts = posts;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleLabel;

        public PostViewHolder(View itemView) {
            super(itemView);
            mTitleLabel = (TextView) itemView.findViewById(R.id.post_title);
        }

        public void setTitle(String title) {
            mTitleLabel.setText(title);
        }
    }

}

