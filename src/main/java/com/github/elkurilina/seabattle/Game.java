package com.github.elkurilina.seabattle;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Game contains rules of playing SeaBattle game.
 *
 * @author Elena Kurilina
 */
public class Game {
    public static final int GRID_SIZE = 10;
    public static final Collection<Integer> SHIP_SIZES = Collections.unmodifiableList(
            Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1));

    public Player playGame(Player p1, Player p2, WriteGameGrid grid1, WriteGameGrid grid2) {
        while (true) {
            if (playRound(p1, grid2)) return p1;
            if (playRound(p2, grid1)) return p2;
        }
    }

    private boolean playRound(Player p1, WriteGameGrid opponent) {
        boolean hit = true;
        boolean victory = !opponent.hasAfloatShip();

        while (hit && !victory) {
            hit = opponent.applyShot(p1.makeShot(opponent.maskedGrid));
            victory = !opponent.hasAfloatShip();
        }

        return victory;
    }

}
