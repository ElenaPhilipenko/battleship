package com.github.elkurilina.seabattle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Elena Kurilina
 */
public class WriteGameGrid extends MaskedGameGrid {

    public WriteGameGrid(MaskedGameGrid grid) {
        super(grid);
    }

    public CellState getCellState(Cell cell) {
        return super.getCellValue(cell);
    }

    public ShotResult applyShot(Cell cell) {
        CellState state = grid.get(cell.x).get(cell.y);
        if (state == CellState.SHIP) {
            setCellAtPoint(cell, CellState.HIT_SHIP);
            if (hasShipCellAround(cell, true)) {
                return ShotResult.SHIP_IS_DEAD;
            }
            return ShotResult.HIT;
        } else if (state == CellState.EMPTY) {
            setCellAtPoint(cell, CellState.MISSED_SHOT);
        }
        return ShotResult.MISSED;
    }

    private boolean hasShipCellAround(Cell cell, boolean checkAround) {
        final Iterable<Cell> surroundPoints = cell.findNeighborsOnField(getSize());
        for (Cell p : surroundPoints) {
            final CellState state = getCellState(p);
            if (state == CellState.SHIP) {
                return false;
            } else if (state == CellState.HIT_SHIP && checkAround) {
                if (!hasShipCellAround(p, false)) {
                    return false;
                }
            }
        }
        return true;
    }


}
