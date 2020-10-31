package com.nsu.learning.myapplication;

import lombok.Getter;

import static com.nsu.learning.myapplication.CellState.CROSS;
import static com.nsu.learning.myapplication.CellState.ZERO;
import static com.nsu.learning.myapplication.GamerOrder.FIRST;
import static com.nsu.learning.myapplication.GamerOrder.SECOND;

@Getter
class GamerInfo {
    private GamerOrder gamerOrder;
    private CellState cellType;

    private GamerInfo(GamerOrder gamerOrder) {
        this.gamerOrder = gamerOrder;
        this.cellType = CROSS;
    }

    static GamerInfo firstPlayerCross() {
        return new GamerInfo(FIRST);
    }

    static GamerInfo secondPlayerCross() {
        return new GamerInfo(SECOND);
    }

    void changeGamer() {
        gamerOrder = FIRST.equals(gamerOrder) ? SECOND : FIRST;
        cellType = CROSS.equals(cellType) ? ZERO : CROSS;
    }
}
