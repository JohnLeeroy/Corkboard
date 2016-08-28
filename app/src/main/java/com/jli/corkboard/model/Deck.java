package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class Deck extends ObservableBaseObject implements IDeck {

    List<Post> mPosts = new ArrayList<>();

    public Deck(String id, String name, List<Post> posts) {
        super(id, name);
        mPosts = posts;
    }

    public Deck(String id, String name) {
        super(id, name);
    }

    protected Deck(Parcel in) {
        super(in);
        mPosts = in.createTypedArrayList(Post.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(mPosts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    public boolean addCard(Post post) {
        boolean result = mPosts.add(post);
        if (result) {
//            setChanged();
//            notifyObservers();
        }
        return result;
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
