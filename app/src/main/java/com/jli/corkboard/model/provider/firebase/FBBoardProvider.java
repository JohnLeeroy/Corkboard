package com.jli.corkboard.model.provider.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jli.corkboard.model.Board;

/**
 * Created by john on 6/19/16.
 */
public class FBBoardProvider extends FirebaseDataProvider<Board> {

    public FBBoardProvider() {
        super();
    }

    @Override
    protected Board getObjectFromSnapshot(DataSnapshot dataSnapshot) {
        Gson gson = new Gson();
        String json = dataSnapshot.getValue().toString();
        String id = dataSnapshot.getKey();
        Board board = new Board(id, gson.fromJson(json, Board.class));
        return board;
    }

    public void getBoard(final String boardId, final ObjectChangeListener<Board> listener, boolean isRecursive) {
        final DatabaseReference dataRef = mDatabase.getReference("board/" + boardId);
        final ValueEventListener dataEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Board board = getObjectFromSnapshot(dataSnapshot);
                listener.onObjectChange(board);
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
