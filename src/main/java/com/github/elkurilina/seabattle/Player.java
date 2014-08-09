package com.github.elkurilina.seabattle;

/**
 * @author Elena Kurilina
 */
public interface Player {

    boolean makeShot(GameGreed greed);

    GameGreed getGameGrid();

    String getName();

}
