package com.github.elkurilina.seabattle;

import java.util.Arrays;

/**
 * @author Elena Kurilina
 */
public class Game {
    public static final int FIELD_SIZE = 10;
    public static final Iterable<Integer> SHIP_SIZES = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);

    public String playGame(Player p1, Player p2, MaskedGameGrid p1GameGrid, MaskedGameGrid p2GameGrid) {
        final WriteGameGrid mask1 = new WriteGameGrid(p1GameGrid);
        final WriteGameGrid mask2 = new WriteGameGrid(p2GameGrid);
        while (!isGameEnded(p1GameGrid, p2GameGrid)) {
            makeShots(p1, mask2, p2GameGrid);
            if (!isGameEnded(p1GameGrid, p2GameGrid)) {
                makeShots(p2, mask1, p1GameGrid);
            }
        }
        return (p1GameGrid.hasAfloatShip() ? p1.getName() : p2.getName());
    }

    private void makeShots(Player p1, WriteGameGrid writeGameGrid, MaskedGameGrid maskedGameGrid) {
        boolean hit = true;
        while (hit) {
            final ShotResult result = writeGameGrid.applyShot(p1.makeShot(maskedGameGrid));
            p1.handleShotResult(result);
            hit = (result == ShotResult.HIT || result == ShotResult.SHIP_IS_DEAD);
        }
    }

    private boolean isGameGridValid(WriteGameGrid gameGrid) {
        return false; //TODO: implement
    }

    private boolean isGameEnded(MaskedGameGrid f1, MaskedGameGrid f2) {
        return !f1.hasAfloatShip() || !f2.hasAfloatShip();
    }


}
