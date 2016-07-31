package com.jli.corkboard.model.provider.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jli.corkboard.model.BoardGroup;
import com.jli.corkboard.model.User;

/**
 * Created by john on 6/19/16.
 */
public final class FBUserProvider extends FirebaseDataProvider<User> {

    public FBUserProvider() {
        super();
    }

    @Override
    protected User getObjectFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue(true).toString();
        String id = dataSnapshot.getKey();
        User user = new User(id ,gson.fromJson(json, User.class));
        return user;
    }

    public void getUser(final String userId, final ObjectChangeListener<User> listener, final boolean isRecursive) {
        final DatabaseReference dataRef = mDatabase.getReference("user/" + userId);
        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = new User(dataSnapshot.getKey(), dataSnapshot.getValue(User.class)); //getObjectFromSnapshot(dataSnapshot);
                if(isRecursive) {
                    getBoardGroups(user, dataSnapshot, listener);
                } else {
                    listener.onObjectChange(user);
                }
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

    void getBoardGroups(final User user, DataSnapshot dataSnapshot, final ObjectChangeListener<User> listener) {
        Iterable<DataSnapshot> boardGroups = dataSnapshot.child("clusters").getChildren();
        FBBoardGroupProvider clusterFBProvider = new FBBoardGroupProvider();
        for(DataSnapshot snapshot : boardGroups) {
            String clusterId = snapshot.getValue().toString();
            clusterFBProvider.getCluster(clusterId, new ObjectChangeListener<BoardGroup>() {
                @Override
                public void onObjectChange(BoardGroup boardGroup) {
                    user.addBoardGroup(boardGroup);
                    listener.onObjectChange(user);
                }
            }, false);
        }
    }
}
