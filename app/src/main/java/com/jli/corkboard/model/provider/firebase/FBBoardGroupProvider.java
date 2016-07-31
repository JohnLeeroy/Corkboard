package com.jli.corkboard.model.provider.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.BoardGroup;

import java.util.HashMap;

/**
 * Created by john on 6/19/16.
 */
public class FBBoardGroupProvider extends FirebaseDataProvider<BoardGroup> {

    public FBBoardGroupProvider() {
        super();
    }

    @Override
    protected BoardGroup getObjectFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue().toString();
        String id = dataSnapshot.getKey();
        BoardGroup boardGroup = new BoardGroup(id, gson.fromJson(json, BoardGroup.class));
        return boardGroup;
    }

    protected BoardGroup[] getObjectsFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue().toString();
        long count = dataSnapshot.getChildrenCount();

        if(count > 1) {
            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
            for(Object boardGroup : map.values()) {
                //BoardGroup group = new BoardGroup()
            }
            return gson.fromJson(json, BoardGroup[].class);
        } else {
            return new BoardGroup[] { gson.fromJson(json, BoardGroup.class) };
        }
    }

    public void getCluster(final String clusterId, final ObjectChangeListener<BoardGroup> listener, final boolean isRecursive) {
        final DatabaseReference dataRef = mDatabase.getReference("board_group/" + clusterId);
        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BoardGroup boardGroup = getObjectFromSnapshot(dataSnapshot);
                if(isRecursive) {
                    getBoards(boardGroup, dataSnapshot, listener);
                } else {
                    listener.onObjectChange(boardGroup);
                }
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

    void getBoards(final BoardGroup boardGroup, DataSnapshot dataSnapshot, final ObjectChangeListener<BoardGroup> listener) {
        Iterable<DataSnapshot> boards = dataSnapshot.child("boards").getChildren();
        FBBoardProvider FBBoardProvider = new FBBoardProvider();
        for(DataSnapshot snapshot : boards) {
            String boardId = snapshot.getValue().toString();
            FBBoardProvider.getBoard(boardId, new ObjectChangeListener<Board>() {
                @Override
                public void onObjectChange(Board board) {
                    boardGroup.addBoard(board);
                    listener.onObjectChange(boardGroup);
                }
            }, true);
        }
    }

    public  void getBoardGroupsForUser(String userId, final ObjectChangeListener<BoardGroup[]> listener) {
        final DatabaseReference dataRef = mDatabase.getReference("board_group");
        Query query = dataRef.orderByChild("users/id").equalTo(userId);

        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BoardGroup[] boardGroups = getObjectsFromSnapshot(dataSnapshot);
                listener.onObjectChange(boardGroups);
                dataRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                dataRef.removeEventListener(this);
            }
        };
        query.addValueEventListener(dataEventListener);
    }

}
