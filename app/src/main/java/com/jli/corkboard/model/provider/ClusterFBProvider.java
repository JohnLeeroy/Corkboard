package com.jli.corkboard.model.provider;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jli.corkboard.model.Cluster;
import com.jli.corkboard.model.User;

/**
 * Created by john on 6/19/16.
 */
public class ClusterFBProvider extends FirebaseDataProvider<Cluster> {

    public ClusterFBProvider() {
        super();
    }

    @Override
    protected Cluster getObjectFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue().toString();
        String id = dataSnapshot.getKey();
        Cluster cluster = new Cluster(id, gson.fromJson(json, Cluster.class));
        return cluster;
    }

    public void getCluster(final String clusterId, final ObjectChangeListener<Cluster> listener) {
        final DatabaseReference dataRef = mDatabase.getReference("cluster/" + clusterId);
        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cluster cluster = getObjectFromSnapshot(dataSnapshot);
                listener.onObjectChange(cluster);
                dataRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                dataRef.removeEventListener(this);
            }
        };
        dataRef.addValueEventListener(dataEventListener);
    }

}
