package com.github.elkurilina.seabattle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GameGrid represents grid of squares with values, where Player can get information
 * about his made shots.
 *
 * @author Elena Kurilina
 */
public class GameGrid {

    protected final List<List<SquareState>> values;
    protected final Collection<GridSquare> squares = new HashSet<>();

    public GameGrid(List<List<SquareState>> values, Collection<GridSquare> squares) {
        this.squares.addAll(squares);
        this.values = values;
    }

    protected GameGrid(Collection<Collection<GridSquare>> shipLocations, int gridSize) {
        this.values = createEmptyGrid(gridSize);
        shipLocations.stream()
                .forEach(squares -> squares
                        .forEach(s -> setSquare(s, SquareState.SHIP)));
    }

    public int getSize() {
        return values.size();
    }

    public SquareState getSquareState(GridSquare gridSquare) {
        final SquareState state = getSquareOpenState(gridSquare);
        if (state == SquareState.SHIP || state == SquareState.EMPTY) {
            return SquareState.HIDDEN;
        }
        return state;
    }

    public Collection<GridSquare> findNotShotSquares() {
        return filterGridSquares(Arrays.asList(SquareState.EMPTY, SquareState.SHIP));
    }

    public Collection<GridSquare> findDeadShips() {
        return filterGridSquares(Arrays.asList(SquareState.DEAD_SHIP));
    }

    public Collection<GridSquare> findHitShip() {
        return filterGridSquares(Arrays.asList(SquareState.HIT));
    }

    protected SquareState getSquareOpenState(GridSquare gridSquare) {
        return values.get(gridSquare.x).get(gridSquare.y);
    }

    protected void setSquare(GridSquare p, SquareState value) {
        values.get(p.x).set(p.y, value);
    }

    private Collection<GridSquare> filterGridSquares(Collection<SquareState> values) {
        return squares.stream()
                .filter(s -> values.contains(getSquareOpenState(s)))
                .collect(Collectors.toSet());
    }

    private List<List<SquareState>> createEmptyGrid(int size) {
        final List<List<SquareState>> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                result.get(i).add(SquareState.EMPTY);
                squares.add(new GridSquare(i, j));
            }
        }
        return result;
    }

}
