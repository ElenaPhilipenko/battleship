package com.github.elkurilina.seabattle;

import com.github.elkurilina.seabattle.player.ConsolePlayer;
import com.github.elkurilina.seabattle.player.RandomPlayer;

import java.io.IOException;

import static com.github.elkurilina.seabattle.Game.FIELD_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class SeaBattle {

    public static void main(String[] args) throws IOException {
        final Game game = new Game();
        final Player p1 = new RandomPlayer(FIELD_SIZE);
        final ConsolePlayer p2 = new ConsolePlayer(FIELD_SIZE);

        final WriteGameGrid p1GameGrid = new WriteGameGrid(p1.getShips(SHIP_SIZES), FIELD_SIZE);
        final WriteGameGrid p2GameGrid = new WriteGameGrid(p2.getShips(SHIP_SIZES), FIELD_SIZE);

        p2.setGreeds(p2GameGrid);

        final String winner = game.playGame(p1, p2, p1GameGrid, p2GameGrid);
        System.out.println("Winner is: " + winner);
    }

}
