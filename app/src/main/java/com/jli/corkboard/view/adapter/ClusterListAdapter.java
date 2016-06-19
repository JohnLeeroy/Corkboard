package com.jli.corkboard.view.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jli.corkboard.R;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.Cluster;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 6/5/16.
 */
public class ClusterListAdapter extends SectioningAdapter {

    private IClickListener mClickListener;

    private class Section {
        int index;
        String header;
        Cluster cluster;
    }

    ArrayList<Section> sections = new ArrayList<>();

    public ClusterListAdapter(List<Cluster> data, IClickListener clickListener) {
        for (int i = 0; i < data.size(); i++) {
            appendSection(data.get(i).getName(), i, data.get(i));
        }
        mClickListener = clickListener;
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.header_title);
        }
    }

    public class ClusterListItemViewHolder extends SectioningAdapter.ItemViewHolder implements View.OnClickListener  {
        private View mRootView;
        private IClickListener mClickListener;

        public ClusterListItemViewHolder(View v, IClickListener mClickListener) {
            super(v);
            mRootView = v;
            this.mClickListener = mClickListener;
            mRootView.setOnClickListener(this);
        }

        public void setTitle(String title) {
            TextView titleLabel = (TextView) mRootView.findViewById(R.id.board_name);
            titleLabel.setText(title);
        }

        public void setImageResource(int resourceId) {
//            ImageView imageView = (ImageView) mRootView.findViewById(R.id.cluster_preview_icon);
//            imageView.setImageResource(resourceId);
        }

        @Override
        public void onClick(View v) {
            int sectionIndex = getSection();
            int index = getPositionInSection();
            Board board = sections.get(sectionIndex).cluster.getBoards().get(index);
            mClickListener.onClick(this, board);
        }
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex) {
        Section s = sections.get(sectionIndex);
        ClusterListItemViewHolder ivh = (ClusterListItemViewHolder) viewHolder;
        ivh.setTitle(s.cluster.getBoards().get(itemIndex).getName());
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.textView.setText(s.header);
    }


    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_simple_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public ClusterListItemViewHolder onCreateItemViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_board_item, parent, false);
        return new ClusterListItemViewHolder(v, mClickListener);
    }


    void appendSection(String header, int index, Cluster cluster) {
        Section section = new Section();
        section.index = index;
        section.header = header;
        section.cluster = cluster;
        sections.add(section);
    }

    void onDeleteSection(int sectionIndex) {
        sections.remove(sectionIndex);
        notifySectionRemoved(sectionIndex);
    }

    void onAppendItem(int sectionIndex, Board board) {
        Section s = sections.get(sectionIndex);
        s.cluster.getBoards().add(board);
        notifySectionItemInserted(sectionIndex, s.cluster.getBoards().size()-1);
    }
    void onDeleteItem(int sectionIndex, int itemIndex) {
        Section s = sections.get(sectionIndex);
        s.cluster.getBoards().remove(itemIndex);
        notifySectionItemRemoved(sectionIndex, itemIndex);
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).cluster.getBoards().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(sections.get(sectionIndex).header);
    }

    public void setData(List<Cluster> modules) {
        for(Cluster cluster : modules) {
            Section section = findClusterInSections(cluster);
            if(section != null) {
                section.cluster = cluster;
            } else {
                appendSection(cluster.getName(), 0, cluster);
            }
        }
        notifyAllSectionsDataSetChanged();
    }

    Section findClusterInSections(Cluster cluster) {
        for(Section c : sections) {
            if(c.cluster.getId().equals(cluster.getId())) {
                return c;
            }
        }
        return null;
    }

    public interface IClickListener {
        void onClick(ClusterListItemViewHolder viewHolder, Board board);
    }
}
