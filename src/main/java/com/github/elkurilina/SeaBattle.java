package com.github.elkurilina;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.player.ConsolePlayer;
import com.github.elkurilina.seabattle.player.RandomPlayer;

import java.io.IOException;

/**
 * @author Elena Kurilina
 */
public class SeaBattle {

    public static void main(String[] args) throws IOException {
        final Game game = new Game();

        final Player p1 = new RandomPlayer();
        final ConsolePlayer p2 = new ConsolePlayer();

        game.playGame(p1, p2);
    }

}
