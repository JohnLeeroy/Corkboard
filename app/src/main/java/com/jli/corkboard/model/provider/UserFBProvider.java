package com.jli.corkboard.model.provider;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jli.corkboard.model.User;

/**
 * Created by john on 6/19/16.
 */
public final class UserFBProvider extends FirebaseDataProvider<User> {

    public UserFBProvider() {
        super();
    }

    @Override
    protected User getObjectFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue().toString();
        String id = dataSnapshot.getKey();
        User user = new User(id ,gson.fromJson(json, User.class));
        return user;
    }

    public void getUser(final String userId, final ObjectChangeListener<User> listener) {
        final DatabaseReference dataRef = mDatabase.getReference("user/" + userId);
        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = getObjectFromSnapshot(dataSnapshot);
                listener.onObjectChange(user);
                dataRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        dataRef.addValueEventListener(dataEventListener);
    }
}
