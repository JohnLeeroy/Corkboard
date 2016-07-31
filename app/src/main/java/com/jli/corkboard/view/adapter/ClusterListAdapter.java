package com.jli.corkboard.view.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jli.corkboard.R;
import com.jli.corkboard.core.model.IBoard;
import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.model.Board;
import com.jli.corkboard.model.BoardGroup;

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
        IBoardGroup boardGroup;
    }

    ArrayList<Section> sections = new ArrayList<>();

    public ClusterListAdapter(List<IBoardGroup> data, IClickListener clickListener) {
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
            IBoard board = sections.get(sectionIndex).boardGroup.getBoards().get(index);
            mClickListener.onClick(this, board);
        }
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex) {
        Section s = sections.get(sectionIndex);
        ClusterListItemViewHolder ivh = (ClusterListItemViewHolder) viewHolder;
        ivh.setTitle(s.boardGroup.getBoards().get(itemIndex).getName());
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


    void appendSection(String header, int index, IBoardGroup boardGroup) {
        Section section = new Section();
        section.index = index;
        section.header = header;
        section.boardGroup = boardGroup;
        sections.add(section);
    }

    void onDeleteSection(int sectionIndex) {
        sections.remove(sectionIndex);
        notifySectionRemoved(sectionIndex);
    }

    void onAppendItem(int sectionIndex, Board board) {
        Section s = sections.get(sectionIndex);
        s.boardGroup.getBoards().add(board);
        notifySectionItemInserted(sectionIndex, s.boardGroup.getBoards().size()-1);
    }
    void onDeleteItem(int sectionIndex, int itemIndex) {
        Section s = sections.get(sectionIndex);
        s.boardGroup.getBoards().remove(itemIndex);
        notifySectionItemRemoved(sectionIndex, itemIndex);
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).boardGroup.getBoards().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(sections.get(sectionIndex).header);
    }

    public void setData(List<IBoardGroup> modules) {
        for(IBoardGroup boardGroup : modules) {
            Section section = findClusterInSections(boardGroup);
            if(section != null) {
                section.boardGroup = boardGroup;
            } else {
                appendSection(boardGroup.getName(), 0, boardGroup);
            }
        }
        notifyAllSectionsDataSetChanged();
    }

    Section findClusterInSections(IBoardGroup boardGroup) {
        for(Section c : sections) {
            if(c.boardGroup.getId().equals(boardGroup.getId())) {
                return c;
            }
        }
        return null;
    }

    public interface IClickListener {
        void onClick(ClusterListItemViewHolder viewHolder, IBoard board);
    }
}
