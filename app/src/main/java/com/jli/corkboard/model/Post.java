package com.jli.corkboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jli.corkboard.core.model.IPost;

/**
 * Created by john on 6/5/16.
 */
public class Post extends ObservableBaseObject implements IPost {

    public Post(String id, String name) {
        super(id, name);
    }

    protected Post(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
