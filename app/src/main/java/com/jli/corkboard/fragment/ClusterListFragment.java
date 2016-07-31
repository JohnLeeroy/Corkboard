package com.jli.corkboard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.view.adapter.ClusterListAdapter;
import com.jli.corkboard.Constant;
import com.jli.corkboard.activity.CorkBoardActivity;
import com.jli.corkboard.R;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.BoardGroup;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ClusterListFragment extends Fragment {

    public static String BOARD_GROUP_ARG = "BOARD_GROUP_ARG";

    RecyclerView mClusterRecyclerView;
    ClusterListAdapter mAdapter;
    List<IBoardGroup> mBoardGroups = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardGroups = getArguments().getParcelableArrayList(BOARD_GROUP_ARG);
        }
    }

    public void setBoardGroups(List<IBoardGroup> boardGroups) {
        mBoardGroups = boardGroups;
        mAdapter.setData(mBoardGroups);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cluster_list, container, false);
        mClusterRecyclerView = (RecyclerView) rootView.findViewById(R.id.cluster_recycler_view);
        mClusterRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        mAdapter = new ClusterListAdapter(mBoardGroups, new ClusterListAdapter.IClickListener() {
            @Override
            public void onClick(ClusterListAdapter.ClusterListItemViewHolder viewHolder, IBoard board) {
                Intent boardActivity = new Intent(getActivity(), CorkBoardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BOARD_ARG, board);
                boardActivity.putExtras(bundle);
                startActivity(boardActivity);
            }

        });
        mClusterRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
