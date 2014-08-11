package com.github.elkurilina.seabattle;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Elena Kurilina
 */
public class WriteGameGrid extends MaskedGameGrid {

    public final MaskedGameGrid maskedGrid;

    public WriteGameGrid(Collection<Cell> shipLocation, int gridSize) {
        super(shipLocation, gridSize);
        maskedGrid = new MaskedGameGrid(grid);
    }

    public CellState getCellState(Cell cell) {
        return super.getCellOpenState(cell);
    }

    public boolean applyShot(Cell cell) {
        final CellState state = grid.get(cell.x).get(cell.y);
        if (state == CellState.SHIP) {
            setCell(cell, CellState.HIT_SHIP);
            final Collection<Cell> ship = findShipByCell(cell);
            if (isShipDead(ship)) {
                markShipAsDead(ship);
            }
            return true;
        } else if (state == CellState.EMPTY) {
            setCell(cell, CellState.MISSED_SHOT);
        }
        return false;
    }

    public boolean hasAfloatShip() {
        for (java.util.List<CellState> row : grid) {
            if (row.contains(CellState.SHIP)) {
                return true;
            }
        }
        return false;
    }

    private void markShipAsDead(Collection<Cell> ship) {
        ship.stream().forEach(cell -> setCell(cell, CellState.DEAD_SHIP));
    }

    private boolean isShipDead(Collection<Cell> ship) {
        return ship.stream().filter(c -> getCellOpenState(c) == CellState.SHIP).count() == 0;
    }

    private Collection<Cell> findShipByCell(Cell ship) {
        final Collection<Cell> shipCells = new HashSet<>();
        boolean shipPresentLeft = true;
        boolean shipPresentRight = true;
        boolean shipPresentUp = true;
        boolean shipPresentDown = true;
        int i = 0;
        while (shipPresentLeft || shipPresentRight || shipPresentDown || shipPresentUp) {
            if (shipPresentLeft) shipPresentLeft = putIfShipCell(ship.translate(-i, 0), shipCells);
            if (shipPresentRight) shipPresentRight = putIfShipCell(ship.translate(i, 0), shipCells);
            if (shipPresentDown) shipPresentDown = putIfShipCell(ship.translate(0, -i), shipCells);
            if (shipPresentUp) shipPresentUp = putIfShipCell(ship.translate(0, i), shipCells);
            i++;
        }
        return shipCells;
    }

    private boolean putIfShipCell(Cell cell, Collection<Cell> shipCells) {
        if (cell.isInside(getSize()) && getCellOpenState(cell).isShip()) {
            shipCells.add(cell);
            return true;
        }
        return false;
    }


}
