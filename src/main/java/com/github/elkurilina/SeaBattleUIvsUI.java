package com.github.elkurilina;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.player.UiPlayer;

import java.io.IOException;

/**
 * @author Elena Kurilina
 */
public class SeaBattleUIvsUI {

    public static void main(String[] args) throws IOException {
        final Game game = new Game();

        final Player p1 = new UiPlayer();
        final Player p2 = new UiPlayer();

        game.playGame(p1, p2);

    }

}
