package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.GameGrid;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.SquareState;
import com.github.elkurilina.seabattle.player.uiplayer.GameApplication;
import com.github.elkurilina.seabattle.player.uiplayer.GameStage;
import com.github.elkurilina.seabattle.player.uiplayer.ShipsCreatingStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author Elena Kurilina
 */
public class UiPlayer implements Player {
    public static final Map<SquareState, Image> IMAGES = new HashMap<>();

    static {
        IMAGES.put(SquareState.MISS, new Image(GameStage.class.getResourceAsStream("/img/miss.png")));
        IMAGES.put(SquareState.HIT, new Image(GameStage.class.getResourceAsStream("/img/hit-ship.png")));
        IMAGES.put(SquareState.DEAD_SHIP, new Image(GameStage.class.getResourceAsStream("/img/dead-ship.png")));
        IMAGES.put(SquareState.SHIP, new Image(GameStage.class.getResourceAsStream("/img/ship.png")));
        IMAGES.put(SquareState.EMPTY, new Image(GameStage.class.getResourceAsStream("/img/empty.png")));
        IMAGES.put(SquareState.HIDDEN, new Image(GameStage.class.getResourceAsStream("/img/empty.png")));
    }


    private final BlockingQueue<GridSquare> nextShot = new SynchronousQueue<>();
    private final BlockingQueue<Collection<Collection<GridSquare>>> ships = new SynchronousQueue<>();

    private GameStage gameStage;
    private ShipsCreatingStage shipsCreatingStage;
    final Application ap = new GameApplication();

    @Override
    public Collection<Collection<GridSquare>> getShips() {
        new JFXPanel();
        Platform.runLater(() -> {
            try {
                shipsCreatingStage = new ShipsCreatingStage(ships);
                ap.start(shipsCreatingStage);
                shipsCreatingStage.createShipsMode();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        try {
            return ships.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initGrids(GameGrid ownGrid, GameGrid opponentGrid) {
        Platform.runLater(() -> {
            try {
                gameStage = new GameStage(nextShot);
                ap.start(gameStage);
                gameStage.opponentGridValues = opponentGrid;
                gameStage.ownGridValues = ownGrid;
                gameStage.gameMode();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public GridSquare makeShot() {
        Platform.runLater(() -> gameStage.updateGrids());
        final GridSquare lastShot;
        try {
            lastShot = nextShot.take();
            return lastShot;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void handleResult(boolean victory) {
        gameStage.showResult(victory);
    }

}
