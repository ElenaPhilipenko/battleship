package com.github.elkurilina.seabattle;

/**
 * @author Elena Kurilina
 */
public class Game {

    public void playGame(Player p1, Player p2) {
        final GameGreed p1GameGreed = p1.getGameGrid();
        final GameGreed p2GameGreed = p2.getGameGrid();

        while (!isGameEnded(p1GameGreed, p2GameGreed)) {
            makeShots(p1, p2GameGreed);
            if (!isGameEnded(p1GameGreed, p2GameGreed)) {
                makeShots(p2, p1GameGreed);
            }
        }
        System.out.println("Winner is: " + detectWinner(p1, p2).getName());
    }

    private void makeShots(Player p1, GameGreed p2GameGreed) {
        boolean hit = true;
        while (hit) {
            hit = p1.makeShot(p2GameGreed);
        }
    }

    private boolean isGameEnded(GameGreed f1, GameGreed f2) {
        return !f1.hasAfloatShip() || !f2.hasAfloatShip();
    }

    private Player detectWinner(Player p1, Player p2) {
        if (p2.getGameGrid().hasAfloatShip()) {
            return p2;
        } else {
            return p1;
        }

    }


}
