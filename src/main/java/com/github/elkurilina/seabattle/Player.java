package com.github.elkurilina.seabattle;

import java.util.Collection;

/**
 * Player interface describes required functionality of SeaBattle player.
 *
 * @author Elena Kurilina
 */
public interface Player {

    GridSquare makeShot(GameGrid grid);

    public Collection<Collection<GridSquare>> getShips();

    String getName();

}
