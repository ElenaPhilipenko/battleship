package com.github.elkurilina.seabattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Elena Kurilina
 */
public class MaskedGameGrid {

    protected final List<List<CellState>> grid;

    public MaskedGameGrid(List<List<CellState>> grid) {
        this.grid = grid;
    }

    protected MaskedGameGrid(Collection<Cell> shipLocation, int gridSize) {
        this.grid = createEmptyGrid(gridSize);
        locateShips(shipLocation);
    }

    public int getSize() {
        return grid.size();
    }

    public CellState getCellState(Cell cell) {
        final CellState state = getCellOpenState(cell);
        if (state == CellState.SHIP || state == CellState.EMPTY) {
            return CellState.HIDDEN;
        }
        return state;
    }

    protected CellState getCellOpenState(Cell cell) {
        return grid.get(cell.x).get(cell.y);
    }

    protected void setCell(Cell p, CellState value) {
        grid.get(p.x).set(p.y, value);
    }

    public List<Cell> findNotShotPoints() {
        return filterGridCell(Arrays.asList(CellState.EMPTY, CellState.SHIP));
    }

    public List<Cell> findDeadShips() {
        return filterGridCell(Arrays.asList(CellState.DEAD_SHIP));
    }

    private List<Cell> filterGridCell(Collection<CellState> values) {
        final List<Cell> empty = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                final Cell current = new Cell(i, j);
                final CellState value = getCellOpenState(current);
                if (values.stream().filter(v -> v == value).count() > 0) {
                    empty.add(current);
                }
            }
        }
        return empty;
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

    private void locateShips(Collection<Cell> shipCells) {
        shipCells.stream().forEach(cell -> setCell(cell, CellState.SHIP));
    }
}
