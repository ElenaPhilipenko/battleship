package com.github.elkurilina;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.player.RandomPlayer;
import com.github.elkurilina.seabattle.player.UiPlayer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Elena Kurilina
 */
public class SeaBattleUI {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final Game game = new Game();

        final Player p1 = new RandomPlayer();
        final UiPlayer p2 = new UiPlayer();

        game.playGame(p1, p2);

    }

}
