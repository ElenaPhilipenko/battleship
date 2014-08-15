package com.github.elkurilina.seabattle;

import java.util.Collection;

/**
 * Player interface describes required functionality of SeaBattle player.
 *
 * @author Elena Kurilina
 */
public interface Player {

    Collection<Collection<GridSquare>> getShips();

    void initGrids(GameGrid ownGrid, GameGrid opponentGrid);

    GridSquare makeShot();

    void handleResult (boolean victory);

}
