package com.jli.corkboard.model.provider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jli.corkboard.model.User;

/**
 * Created by john on 6/19/16.
 */
public abstract class FirebaseDataProvider<T> {

    public interface ObjectChangeListener<T> {
        void onObjectChange(T object);
    }

    protected FirebaseDatabase mDatabase;

    public FirebaseDataProvider() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    protected abstract T getObjectFromSnapshot(DataSnapshot dataSnapshot);
}
