package com.github.elkurilina.seabattle;

import java.util.Collection;
import java.util.HashSet;

/**
 * GridSquare represents individual square in the grid identified by it's position.
 *
 * @author Elena Kurilina
 */
public class GridSquare {
    public final int x;
    public final int y;

    public GridSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GridSquare translate(int tx, int ty) {
        return new GridSquare(x + tx, y + ty);
    }

    public GridSquare translate(int t, boolean horizontal) {
        return horizontal ? new GridSquare(x + t, y) : new GridSquare(x, y + t);
    }

    public boolean isInside(int gridSize) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public double distance(GridSquare c){
        return Math.sqrt(Math.pow((x - c.x), 2) + Math.pow((y - c.y), 2));
    }

    public Collection<GridSquare> findNeighborsOnGridWithDiagonals(int gridSize) {
        final Collection<GridSquare> surroundingPoints = findNeighborsOnGrid(gridSize);
        validateAndAdd(x - 1, y - 1, surroundingPoints, gridSize);
        validateAndAdd(x - 1, y + 1, surroundingPoints, gridSize);
        validateAndAdd(x + 1, y - 1, surroundingPoints, gridSize);
        validateAndAdd(x + 1, y + 1, surroundingPoints, gridSize);

        return surroundingPoints;
    }

    public Collection<GridSquare> findNeighborsOnGrid(int gridSize) {
        final Collection<GridSquare> surroundingPoints = new HashSet<>();
        validateAndAdd(x + 1, y, surroundingPoints, gridSize);
        validateAndAdd(x - 1, y, surroundingPoints, gridSize);
        validateAndAdd(x, y + 1, surroundingPoints, gridSize);
        validateAndAdd(x, y - 1, surroundingPoints, gridSize);

        return surroundingPoints;
    }

    private void validateAndAdd(int x, int y, Collection<GridSquare> validPoints, int size) {
        final GridSquare gridSquare = new GridSquare(x, y);
        if (gridSquare.isInside(size)) {
            validPoints.add(gridSquare);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridSquare gridSquare = (GridSquare) o;

        if (x != gridSquare.x) return false;
        if (y != gridSquare.y) return false;

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
        return "GridSquare{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
