package com.jli.corkboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.jli.corkboard.fragment.ClusterListFragment;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.Cluster;
import com.jli.corkboard.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    User mCurrentUser;
    ClusterListFragment mClusterListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mClusterListFragment = (ClusterListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_cluster_layout);
        mCurrentUser = createDefaultUser();
        Cluster groceryCluster = mCurrentUser.getCluster("Grocery List");
        Cluster todoCluster = mCurrentUser.getCluster("Books");
        Cluster vacaCluster = mCurrentUser.getCluster("Vacation Research");
        List<Cluster> clusterList = new ArrayList<>();
        clusterList.add(groceryCluster);
        clusterList.add(todoCluster);
        clusterList.add(vacaCluster);
        mClusterListFragment.setClusters(clusterList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBoardDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    User createDefaultUser() {
        User defaultUser = new User(UUID.randomUUID().toString(), "John");
        Cluster mGroceryList = new Cluster(UUID.randomUUID().toString(), "Grocery List");
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Publix"));
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Sams Club"));
        mGroceryList.addBoard(new Board(UUID.randomUUID().toString(), "Etc"));
        Cluster mTodo = new Cluster(UUID.randomUUID().toString(), "Books");
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Atlas Shrugged"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "1984"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Les Miserables"));
        mTodo.addBoard(new Board(UUID.randomUUID().toString(), "Astonishing X-Men Series"));
        Cluster mVacationSpots = new Cluster(UUID.randomUUID().toString(), "Vacation Research");
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Montreal - Canada"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Barcelona - Spain"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Paris - Spain"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "Amsterdam - Germany"));
        mVacationSpots.addBoard(new Board(UUID.randomUUID().toString(), "NOLA - Louisiana"));

        defaultUser.addCluster(mGroceryList);
        defaultUser.addCluster(mTodo);
        defaultUser.addCluster(mVacationSpots);
        return defaultUser;
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
        Board board = new Board(UUID.randomUUID().toString(), name);
        Cluster cluster = mCurrentUser.getCluster(clusterName);
        if(cluster == null) {
            cluster = new Cluster(UUID.randomUUID().toString(), clusterName);
            mCurrentUser.addCluster(cluster);
        }
        cluster.addBoard(board);
        mClusterListFragment.setClusters(mCurrentUser.getClusters());
    }

    List<String> getClusterNames(User user) {
        List<Cluster> clusters = user.getClusters();
        List<String> names = new ArrayList<>();
        for(Cluster cluster : clusters) {
            names.add(cluster.getName());
        }
        return names;
    }

}
