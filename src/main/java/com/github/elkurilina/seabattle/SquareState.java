package com.github.elkurilina.seabattle;

/**
 * SquareState represents possible states of a square in the grid.
 *
 * @author Elena Kurilina
 */
public enum SquareState {
    SHIP("s "), HIT("! "), MISS(". "), EMPTY("- "), HIDDEN("  "), DEAD_SHIP("X");
    private final String value;

    SquareState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isShip() {
        return this == SHIP || this == DEAD_SHIP || this == HIT;
    }
}
