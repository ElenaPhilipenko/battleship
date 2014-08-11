package com.github.elkurilina.seabattle;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public class Game {
    public static final int FIELD_SIZE = 10;
    public static final Collection<Integer> SHIP_SIZES = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);

    public String playGame(Player p1, Player p2, WriteGameGrid p1GameGrid, WriteGameGrid p2GameGrid) {
        while (!isGameEnded(p1GameGrid, p2GameGrid)) {
            makeShots(p1, p2GameGrid);
            if (!isGameEnded(p1GameGrid, p2GameGrid)) {
                makeShots(p2, p1GameGrid);
            }
        }
        return (p1GameGrid.hasAfloatShip() ? p1.getName() : p2.getName());
    }

    private void makeShots(Player p1, WriteGameGrid writeGameGrid) {
        boolean hit = true;
        while (hit && writeGameGrid.hasAfloatShip()) {
            hit = writeGameGrid.applyShot(p1.makeShot(writeGameGrid.maskedGrid));
        }
    }

    private boolean isGameGridValid(WriteGameGrid gameGrid) {
        return false; //TODO: implement
    }

    private boolean isGameEnded(WriteGameGrid f1, WriteGameGrid f2) {
        return !(f1.hasAfloatShip() && f2.hasAfloatShip());
    }


}
