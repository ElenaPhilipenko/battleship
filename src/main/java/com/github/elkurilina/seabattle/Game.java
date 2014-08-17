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
        final Map<Player, WriteGameGrid> playerToGrid = initGrids(p1, p2);

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

    private Map<Player, WriteGameGrid> initGrids(Player p1, Player p2) {
        final Map<Player, WriteGameGrid> playerToGrid = new HashMap<>();

        final WriteGameGrid[] p2GameGrid = {null};
        final WriteGameGrid[] p1GameGrid = {null};

        Thread thread = new Thread(() -> {
            p1GameGrid[0] = WriteGameGrid.createGameGidWithShips(p1.getShips(), GRID_SIZE);
            playerToGrid.put(p1, p1GameGrid[0]);
        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            p2GameGrid[0] = WriteGameGrid.createGameGidWithShips(p2.getShips(), GRID_SIZE);
            playerToGrid.put(p2, p2GameGrid[0]);
        });
        thread2.start();

        try {
            thread2.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p2.initGrids(p2GameGrid[0], p1GameGrid[0].maskedGrid);
        p1.initGrids(p1GameGrid[0], p2GameGrid[0].maskedGrid);
        return playerToGrid;
    }

    private boolean killEveryBody(Player p1, WriteGameGrid opponent) {
        boolean victory;
        boolean hit;
        do {
            hit = opponent.applyShot(p1.makeShot());
            victory = !opponent.hasAfloatShip();
        }
        while (hit && !victory);

        return victory;
    }

}
