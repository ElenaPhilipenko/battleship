package com.github.elkurilina.seabattle;

import com.github.elkurilina.seabattle.player.ConsolePlayer;
import com.github.elkurilina.seabattle.player.RandomPlayer;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Elena Kurilina
 */
public class SeaBattle {

    public static final int FIELD_SIZE = 10;
    public static final Iterable<Integer> SHIP_SIZES = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.playGame(new RandomPlayer(SHIP_SIZES, FIELD_SIZE), new ConsolePlayer(SHIP_SIZES, FIELD_SIZE));
    }
}
