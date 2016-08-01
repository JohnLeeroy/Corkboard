package com.jli.corkboard.model.provider;

import com.jli.corkboard.core.model.IBoardGroup;
import com.jli.corkboard.core.repository.specification.SpecificationById;
import com.jli.corkboard.model.repository.BoardGroupRepository;
import com.jli.corkboard.model.repository.BoardRepository;

public class BoardGroupProvider {

    //Only keep on instance
    private static final BoardGroupRepository mBoardGroupRepo = new BoardGroupRepository();

    public IBoardGroup getBoardGroup(String id) {
        SpecificationById<IBoardGroup> spec = new SpecificationById<>(id);
        IBoardGroup group = mBoardGroupRepo.query(spec).get(0);
        if(group != null) {

        }
        return group;
    }

}
