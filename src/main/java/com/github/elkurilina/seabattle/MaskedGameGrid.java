package com.github.elkurilina.seabattle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public class MaskedGameGrid {

    protected final List<List<CellState>> grid;

    public MaskedGameGrid(MaskedGameGrid grid) {
        this.grid = grid.grid;
    }

    public MaskedGameGrid(Iterable<Cell> shipLocation, int gridSize) {
        this.grid = createEmptyGrid(gridSize);
        locateShips(shipLocation);
    }

    public int getSize() {
        return grid.size();
    }

    public CellState getCellState(Cell cell) {
        final CellState state = grid.get(cell.x).get(cell.y);
        if (state == CellState.SHIP || state == CellState.EMPTY) {
            return CellState.HIDDEN;
        }
        return state;
    }

    protected CellState getCellValue(Cell cell) {
        return grid.get(cell.x).get(cell.y);
    }

    public boolean hasAfloatShip() {
        for (List<CellState> row : grid) {
            if (row.contains(CellState.SHIP)) {
                return true;
            }
        }
        return false;
    }


    public List<Cell> findNotShotPoints() {
        final List<Cell> empty = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                final Cell current = new Cell(i, j);
                if (getCellValue(current) == CellState.EMPTY || getCellValue(current) == CellState.SHIP) {
                    empty.add(current);
                }
            }
        }
        return empty;
    }

    protected void setCellAtPoint(Cell p, CellState value) {
        grid.get(p.x).set(p.y, value);
    }

    private List<List<CellState>> createEmptyGrid(int size) {
        final List<List<CellState>> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                result.get(i).add(CellState.EMPTY);
            }
        }
        return result;
    }

    private void locateShips(Iterable<Cell> shipCells) {
        for (Cell p : shipCells) {
            setCellAtPoint(p, CellState.SHIP);
        }
    }
}
