package com.jli.corkboard.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jli.corkboard.EventConstant;
import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.core.model.IUser;
import com.jli.corkboard.model.BoardGroup;
import com.jli.corkboard.view.InstantAutoCompleteTextView;
import com.jli.corkboard.R;
import com.jli.corkboard.fragment.ClusterListFragment;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity {
    private static boolean isFirebasePersistant = false;

    IUser mCurrentUser;
    ClusterListFragment mClusterListFragment;
    FirebaseAnalytics mFirebaseAnalytics;

    Board testBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mClusterListFragment = (ClusterListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_cluster_layout);
        mCurrentUser = createDefaultUser();
        mClusterListFragment.setBoardGroups(mCurrentUser.getBoardGroups());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBoardDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mCurrentUser = createDefaultUser();

        testBoard = new Board("2134u983q257043", "Default Name");

        Observable<Board> boardObservable = Observable.just(testBoard);
        boardObservable.subscribe(new Observer<Board>() {
            @Override
            public void onCompleted() {
                System.out.println(testBoard.getName());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Board b) {
                System.out.println(b.getName());

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAddBoardDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_create_board, null);
        dialogBuilder.setView(dialogView);

        final EditText editNameField = (EditText) dialogView.findViewById(R.id.board_name_field);
        final InstantAutoCompleteTextView clusterNameField = (InstantAutoCompleteTextView) dialogView.findViewById(R.id.board_group_field);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getClusterNames(mCurrentUser));
        clusterNameField.setAdapter(adapter);
        clusterNameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clusterNameField.showDropDown();
            }
        });

        dialogBuilder.setTitle("Create Board");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                addNewBoard(editNameField.getText().toString(), clusterNameField.getText().toString());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        b.show();
    }

    void addNewBoard(String name, String clusterName) {
        IBoard board = new Board(UUID.randomUUID().toString(), name);
        IBoardGroup boardGroup = mCurrentUser.getCluster(clusterName);
        if (boardGroup == null) {
            boardGroup = new BoardGroup(UUID.randomUUID().toString(), clusterName);
            mCurrentUser.addBoardGroup(boardGroup);
            mClusterListFragment.setBoardGroups(mCurrentUser.getBoardGroups());
        }
        boardGroup.addBoard(board);
        mClusterListFragment.setBoardGroups(mCurrentUser.getBoardGroups());
        mFirebaseAnalytics.logEvent(EventConstant.CLUSTER_CREATED_EVENT, null);
    }

    List<String> getClusterNames(IUser user) {
        List<IBoardGroup> boardGroups = user.getBoardGroups();
        List<String> names = new ArrayList<>();
        for (IBoardGroup boardGroup : boardGroups) {
            names.add(boardGroup.getName());
        }
        return names;
    }

    IUser createDefaultUser() {
        User defaultUser = new User(UUID.randomUUID().toString(), "John");
        BoardGroup mGroceryList = new BoardGroup(UUID.randomUUID().toString(), "Grocery List");
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Publix"));
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Sams Club"));
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Etc"));
        BoardGroup mTodo = new BoardGroup(UUID.randomUUID().toString(), "Books");
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Atlas Shrugged"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "1984"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Les Miserables"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Astonishing X-Men Series"));
        BoardGroup mVacationSpots = new BoardGroup(UUID.randomUUID().toString(), "Vacation Research");
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Barcelona - Spain"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Paris - Spain"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Amsterdam - Germany"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "NOLA - Louisiana"));

        defaultUser.addBoardGroup(mGroceryList);
        defaultUser.addBoardGroup(mTodo);
        defaultUser.addBoardGroup(mVacationSpots);
        return defaultUser;
    }
}
