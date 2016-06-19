package com.jli.corkboard.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jli.corkboard.Constant;
import com.jli.corkboard.EventConstant;
import com.jli.corkboard.R;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.Deck;
import com.jli.corkboard.view.adapter.DeckViewPagerAdapter;

public class CorkBoardActivity extends AppCompatActivity {

    ViewPager mViewPager;
    DeckViewPagerAdapter mAdapter;
    BroadcastReceiver mLocalBroadcastReceiver;
    Board mBoard;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cork_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle;
        if(savedInstanceState != null) {
            bundle = savedInstanceState;
        } else {
            bundle = getIntent().getExtras();
        }
        mBoard = bundle.getParcelable(Constant.BOARD_ARG);
        getSupportActionBar().setTitle(mBoard.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        bindUi();
        bindLocalBroadcast();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocalBroadcastReceiver);
    }

    void bindUi() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager_deck);
        mAdapter = new DeckViewPagerAdapter(getSupportFragmentManager(), mBoard.getDecks());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageMargin(-100);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter.notifyDataSetChanged();
    }

    void bindLocalBroadcast() {
        mLocalBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(Constant.ADD_NEW_DECK_BROADCAST)) {
                    Bundle bundle = intent.getExtras();
                    Deck deck = bundle.getParcelable(Constant.DECK_ARG);
                    mBoard.addDeck(deck);
                    mAdapter.notifyDataSetChanged();
                    mFirebaseAnalytics.logEvent(EventConstant.DECK_CREATED_EVENT, null);
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ADD_NEW_DECK_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalBroadcastReceiver, filter);
    }

    //Many Post Collection
    // Many Cards
    // Add Cards
    // Add Post Collection
    //
    // Horizontal Linear Layout
    //   Post Collection Post
    //     Many Cards

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(getIntent().getExtras());
        outState.putParcelable(Constant.BOARD_ARG, mBoard);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
