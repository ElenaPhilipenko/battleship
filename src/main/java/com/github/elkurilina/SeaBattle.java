package com.github.elkurilina;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.WriteGameGrid;
import com.github.elkurilina.seabattle.player.ConsolePlayer;
import com.github.elkurilina.seabattle.player.RandomPlayer;

import java.io.IOException;

import static com.github.elkurilina.seabattle.Game.GRID_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class SeaBattle {

    public static void main(String[] args) throws IOException {
        final Game game = new Game();
        final Player p1 = new RandomPlayer(GRID_SIZE);
        final ConsolePlayer p2 = new ConsolePlayer(GRID_SIZE);

        final WriteGameGrid p1GameGrid = WriteGameGrid.createGameGidWithShips(p1.getShips(SHIP_SIZES), GRID_SIZE);
        final WriteGameGrid p2GameGrid = WriteGameGrid.createGameGidWithShips(p2.getShips(SHIP_SIZES), GRID_SIZE);

        p2.setGreeds(p2GameGrid);

        final String winner = game.playGame(p1, p2, p1GameGrid, p2GameGrid);
        System.out.println("Winner is: " + winner);
    }

}
