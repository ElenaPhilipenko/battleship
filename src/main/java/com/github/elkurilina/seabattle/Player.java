package com.github.elkurilina.seabattle;

/**
 * @author Elena Kurilina
 */
public interface Player {

    Cell makeShot(MaskedGameGrid gameGrid);

    boolean handleShotResult(ShotResult shotResult);

    public Iterable<Cell> getShips(Iterable<Integer> shipSizes);

    String getName();

}
