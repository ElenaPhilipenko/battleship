package com.github.elkurilina.seabattle;

import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public interface Player {

    Cell makeShot(MaskedGameGrid gameGrid);

    boolean handleShotResult(ShotResult shotResult);

    public Collection<Cell> getShips(Iterable<Integer> shipSizes);

    String getName();

}
