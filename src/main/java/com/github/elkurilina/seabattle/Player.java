package com.github.elkurilina.seabattle;

import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public interface Player {

    Cell makeShot(GameGrid grid);

    public Collection<Collection<Cell>> getShips(Iterable<Integer> shipSizes);

    String getName();

}
