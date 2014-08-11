package com.github.elkurilina.seabattle;

import java.awt.*;
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

    public Cell translate(Point p) {
        return new Cell(x + p.x, y + p.y);
    }

    public boolean isInside(int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
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
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
