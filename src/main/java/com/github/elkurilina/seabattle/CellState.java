package com.github.elkurilina.seabattle;

/**
 * @author Elena Kurilina
 */
public enum CellState {
    SHIP, HIT_SHIP, MISSED_SHOT, EMPTY, HIDDEN, DEAD_SHIP;

    public boolean isShip() {
        return this == SHIP || this == DEAD_SHIP || this == HIT_SHIP;
    }
}
