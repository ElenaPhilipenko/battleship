package com.github.elkurilina.seabattle;

/**
 * @author Elena Kurilina
 */
public class WriteGameGrid extends MaskedGameGrid {

    public final MaskedGameGrid maskedGrid;

    public WriteGameGrid(Iterable<Cell> shipLocation, int gridSize) {
        super(shipLocation, gridSize);
        maskedGrid = new MaskedGameGrid(grid);
    }

    public CellState getCellState(Cell cell) {
        return super.getCellValue(cell);
    }

    public ShotResult applyShot(Cell cell) {
        CellState state = grid.get(cell.x).get(cell.y);
        if (state == CellState.SHIP) {
            setCellAtPoint(cell, CellState.HIT_SHIP);
            if (!hasShipCellAround(cell, true)) {
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
                return true;
            } else if (state == CellState.HIT_SHIP && checkAround) {
                if (hasShipCellAround(p, false)) {
                    return true;
                }
            }
        }
        return false;
    }


}
