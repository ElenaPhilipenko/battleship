package com.github.elkurilina;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.WriteGameGrid;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import com.github.elkurilina.seabattle.player.UiPlayer;

import java.io.IOException;

import static com.github.elkurilina.seabattle.Game.GRID_SIZE;
import static com.github.elkurilina.seabattle.Game.SHIP_SIZES;

/**
 * @author Elena Kurilina
 */
public class SeaBattleUI {

    public static void main(String[] args) throws IOException {
        final Game game = new Game();
        final Player p1 = new RandomPlayer(GRID_SIZE);
        final UiPlayer p2 = new UiPlayer();

        final WriteGameGrid p1GameGrid = WriteGameGrid.createGameGidWithShips(p1.getShips(SHIP_SIZES), GRID_SIZE);
        final WriteGameGrid p2GameGrid = WriteGameGrid.createGameGidWithShips(p2.getShips(SHIP_SIZES), GRID_SIZE);

        p2.setGrid(p2GameGrid);

        final Player winner = game.playGame(p1, p2, p1GameGrid, p2GameGrid);
        p2.handleResult(winner.equals(p2));
        System.out.println("Winner is: " + winner.getName());
    }

}
