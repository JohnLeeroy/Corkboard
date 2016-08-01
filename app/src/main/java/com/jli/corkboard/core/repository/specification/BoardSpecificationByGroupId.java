package com.jli.corkboard.core.repository.specification;

import com.jli.corkboard.core.model.IBoard;

public class BoardSpecificationByGroupId implements Specification<IBoard> {

    String mBoardGroupId;

    public BoardSpecificationByGroupId(String boardGroupId) {
        mBoardGroupId = boardGroupId;
    }

    @Override
    public boolean specified(IBoard object) {
        return object.getBoardGroupId().equals(mBoardGroupId);
    }
}
