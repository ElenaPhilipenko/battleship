package com.github.elkurilina.seabattle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Elena Kurilina
 */
public class GameGrid {

    protected final List<List<CellState>> values;
    protected final Collection<Cell> cells = new HashSet<>();

    public GameGrid(List<List<CellState>> values, Collection<Cell> cells) {
        this.cells.addAll(cells);
        this.values = values;
    }

    protected GameGrid(Collection<Collection<Cell>> shipLocations, int gridSize) {
        this.values = createEmptyGrid(gridSize);
        shipLocations.stream()
                .forEach(cells -> cells
                        .forEach(cell -> setCell(cell, CellState.SHIP)));
    }

    public int getSize() {
        return values.size();
    }

    public CellState getCellState(Cell cell) {
        final CellState state = getCellOpenState(cell);
        if (state == CellState.SHIP || state == CellState.EMPTY) {
            return CellState.HIDDEN;
        }
        return state;
    }

    public Collection<Cell> findNotShotPoints() {
        return filterGridCells(Arrays.asList(CellState.EMPTY, CellState.SHIP));
    }

    public Collection<Cell> findDeadShips() {
        return filterGridCells(Arrays.asList(CellState.DEAD_SHIP));
    }

    public Collection<Cell> findHitShip() {
        return filterGridCells(Arrays.asList(CellState.HIT_SHIP));
    }

    protected CellState getCellOpenState(Cell cell) {
        return values.get(cell.x).get(cell.y);
    }

    protected void setCell(Cell p, CellState value) {
        values.get(p.x).set(p.y, value);
    }

    private Collection<Cell> filterGridCells(Collection<CellState> values) {
        return cells.stream()
                .filter(cell -> values.contains(getCellOpenState(cell)))
                .collect(Collectors.toSet());
    }

    private List<List<CellState>> createEmptyGrid(int size) {
        final List<List<CellState>> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                result.get(i).add(CellState.EMPTY);
                cells.add(new Cell(i, j));
            }
        }
        return result;
    }

}
