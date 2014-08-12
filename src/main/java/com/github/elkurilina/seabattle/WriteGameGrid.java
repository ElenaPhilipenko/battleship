package com.github.elkurilina.seabattle;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * WriteGameGrid extends GameGrid by abilities to modify state of the grid
 * and get information about ships.
 * Each object contains reference to masked version of itself (GameGrid object),
 * which can be given to an opponent.
 *
 * @author Elena Kurilina
 */
public class WriteGameGrid extends GameGrid {

    public final GameGrid maskedGrid;

    public static WriteGameGrid createGameGidWithShips(Collection<Collection<GridSquare>> shipLocation, int gridSize) {
        if (GameGridValidator.isShipLocationsValid(shipLocation)) {
            return new WriteGameGrid(shipLocation, gridSize);
        } else {
            throw new IllegalArgumentException("Ship location is not valid");
        }
    }

    private WriteGameGrid(Collection<Collection<GridSquare>> shipLocation, int gridSize) {
        super(shipLocation, gridSize);
        maskedGrid = new GameGrid(values, squares);
    }

    public SquareState getSquareState(GridSquare gridSquare) {
        return super.getSquareOpenState(gridSquare);
    }

    public boolean applyShot(GridSquare gridSquare) {
        final SquareState state = getSquareOpenState(gridSquare);
        if (state == SquareState.SHIP) {
            setSquare(gridSquare, SquareState.HIT);
            final Collection<GridSquare> ship = findShipBySquare(gridSquare);
            if (isShipDead(ship)) {
                markShipAsDead(ship);
            }
            return true;
        } else if (state == SquareState.EMPTY) {
            setSquare(gridSquare, SquareState.MISS);
        }
        return false;
    }

    public boolean hasAfloatShip() {
        for (List<SquareState> row : values) {
            if (row.contains(SquareState.SHIP)) {
                return true;
            }
        }
        return false;
    }

    private void markShipAsDead(Collection<GridSquare> ship) {
        ship.stream().forEach(s -> setSquare(s, SquareState.DEAD_SHIP));
    }

    private boolean isShipDead(Collection<GridSquare> ship) {
        return ship.stream().filter(c -> getSquareOpenState(c) == SquareState.SHIP).count() == 0;
    }

    private Collection<GridSquare> findShipBySquare(GridSquare ship) {
        final Collection<GridSquare> shipGridSquares = new HashSet<>();
        boolean shipPresentLeft = true;
        boolean shipPresentRight = true;
        boolean shipPresentUp = true;
        boolean shipPresentDown = true;
        int i = 0;
        while (shipPresentLeft || shipPresentRight || shipPresentDown || shipPresentUp) {
            if (shipPresentLeft) shipPresentLeft = putIfShip(ship.translate(-i, 0), shipGridSquares);
            if (shipPresentRight) shipPresentRight = putIfShip(ship.translate(i, 0), shipGridSquares);
            if (shipPresentDown) shipPresentDown = putIfShip(ship.translate(0, -i), shipGridSquares);
            if (shipPresentUp) shipPresentUp = putIfShip(ship.translate(0, i), shipGridSquares);
            i++;
        }
        return shipGridSquares;
    }

    private boolean putIfShip(GridSquare gridSquare, Collection<GridSquare> shipGridSquares) {
        if (gridSquare.isInside(getSize()) && getSquareOpenState(gridSquare).isShip()) {
            shipGridSquares.add(gridSquare);
            return true;
        }
        return false;
    }

}
