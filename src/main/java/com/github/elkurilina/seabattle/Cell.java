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

    public Collection<Cell> findNeighborsOnFieldWithDiagonals(int size) {
        final Collection<Cell> surroundingPoints = findNeighborsOnField(size);
        validateAndAdd(x - 1, y - 1, surroundingPoints, size);
        validateAndAdd(x - 1, y + 1, surroundingPoints, size);
        validateAndAdd(x + 1, y - 1, surroundingPoints, size);
        validateAndAdd(x + 1, y + 1, surroundingPoints, size);

        return surroundingPoints;

    }

    public Collection<Cell> findNeighborsOnField(int size) {
        final Collection<Cell> surroundingPoints = new HashSet<>();
        validateAndAdd(x + 1, y, surroundingPoints, size);
        validateAndAdd(x - 1, y, surroundingPoints, size);
        validateAndAdd(x, y + 1, surroundingPoints, size);
        validateAndAdd(x, y - 1, surroundingPoints, size);

        return surroundingPoints;

    }

    private void validateAndAdd(int x, int y, Collection<Cell> validPoints, int size) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            validPoints.add(new Cell(x, y));
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
}
