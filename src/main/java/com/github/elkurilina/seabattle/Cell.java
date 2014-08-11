package com.github.elkurilina.seabattle;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Elena Kurilina
 */
public class Cell {
    public final int x;
    public final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell translate(int tx, int ty) {
        return new Cell(x + tx, y + ty);
    }

    public Cell translate(int t, boolean horizontal) {
        return horizontal ? new Cell(x + t, y) : new Cell(x, y + t);
    }

    public boolean isInside(int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public Collection<Cell> findNeighborsOnGridWithDiagonals(int gridSize) {
        final Collection<Cell> surroundingPoints = findNeighborsOnGrid(gridSize);
        validateAndAdd(x - 1, y - 1, surroundingPoints, gridSize);
        validateAndAdd(x - 1, y + 1, surroundingPoints, gridSize);
        validateAndAdd(x + 1, y - 1, surroundingPoints, gridSize);
        validateAndAdd(x + 1, y + 1, surroundingPoints, gridSize);

        return surroundingPoints;
    }

    public Collection<Cell> findNeighborsOnGrid(int gridSize) {
        final Collection<Cell> surroundingPoints = new HashSet<>();
        validateAndAdd(x + 1, y, surroundingPoints, gridSize);
        validateAndAdd(x - 1, y, surroundingPoints, gridSize);
        validateAndAdd(x, y + 1, surroundingPoints, gridSize);
        validateAndAdd(x, y - 1, surroundingPoints, gridSize);

        return surroundingPoints;
    }

    private void validateAndAdd(int x, int y, Collection<Cell> validPoints, int size) {
        final Cell cell = new Cell(x, y);
        if (cell.isInside(size)) {
            validPoints.add(cell);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
