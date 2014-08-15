package com.github.elkurilina.seabattle.player;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.GameGrid;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.Player;
import com.github.elkurilina.seabattle.player.uiplayer.UiGameWindow;
import javafx.application.Application;

import java.util.Collection;

/**
 * @author Elena Kurilina
 */
public class UiPlayer implements Player {
    public final static Monitor monitor = new Monitor();
    private GameGrid opponentGrid;

    @Override
    public Collection<Collection<GridSquare>> getShips() {
        new Thread(() -> Application.launch(UiGameWindow.class)).start();
        UiGameWindow.createShipsMode();
        synchronized (UiGameWindow.ships) {
            while (UiGameWindow.ships.size() != Game.SHIP_SIZES.size()) {
                try {
                    UiGameWindow.ships.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return UiGameWindow.ships;
        }
    }

    @Override
    public void initGrids(GameGrid ownGrid, GameGrid opponentGrid) {
        this.opponentGrid = opponentGrid;
        UiGameWindow.myGrid = ownGrid;
        UiGameWindow.gameMode();
    }

    @Override
    public GridSquare makeShot() {
        UiGameWindow.opponentGrid = opponentGrid;
        UiGameWindow.updateGrids();
        synchronized (monitor) {
            while (monitor.nextShot == null) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            final GridSquare lastShot = monitor.nextShot;
            monitor.nextShot = null;
            return lastShot;
        }
    }

    @Override
    public void handleResult(boolean victory) {
        UiGameWindow.showResult(victory);
    }

    public static class Monitor {
        public GridSquare nextShot;
    }

}
