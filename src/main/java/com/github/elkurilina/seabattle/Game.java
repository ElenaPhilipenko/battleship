package com.github.elkurilina.seabattle;

import java.util.*;

/**
 * Game contains rules of playing SeaBattle game.
 *
 * @author Elena Kurilina
 */
public class Game {
    public static final int GRID_SIZE = 10;
    public static final Collection<Integer> SHIP_SIZES = Collections.unmodifiableList(
            Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1));

    public void playGame(Player p1, Player p2) {
        final WriteGameGrid p1GameGrid = WriteGameGrid.createGameGidWithShips(p1.getShips(), GRID_SIZE);
        final WriteGameGrid p2GameGrid = WriteGameGrid.createGameGidWithShips(p2.getShips(), GRID_SIZE);

        p2.initGrids(p2GameGrid, p1GameGrid.maskedGrid);
        p1.initGrids(p1GameGrid, p2GameGrid.maskedGrid);

        final Map<Player, WriteGameGrid> playerToGrid = new HashMap<>();
        playerToGrid.put(p1, p1GameGrid);
        playerToGrid.put(p2, p2GameGrid);

        Player current = p1;
        Player opponent = p2;
        while (!killEveryBody(current, playerToGrid.get(opponent))) {
            Player t = current;
            current = opponent;
            opponent = t;
        }
        current.handleResult(true);
        opponent.handleResult(false);
    }

    private boolean killEveryBody(Player p1, WriteGameGrid opponent) {
        boolean victory;
        boolean hit;
        do {
            hit = opponent.applyShot(p1.makeShot());
            victory = !opponent.hasAfloatShip();
        }
        while (!hit && !victory);

        return victory;
    }

}
