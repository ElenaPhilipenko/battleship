package com.github.elkurilina.seabattle;

/**
 * SquareState represents possible states of a square in the grid.
 *
 * @author Elena Kurilina
 */
public enum SquareState {
    SHIP, HIT, MISS, EMPTY, HIDDEN, DEAD_SHIP;

    public boolean isShip() {
        return this == SHIP || this == DEAD_SHIP || this == HIT;
    }
}
