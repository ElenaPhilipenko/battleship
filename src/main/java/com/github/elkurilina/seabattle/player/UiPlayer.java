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

    @Override
    public GridSquare makeShot(GameGrid grid) {
        UiGameWindow.opponentGrid = grid;
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
    public Collection<Collection<GridSquare>> getShips(Iterable<Integer> shipSizes) {
        new Thread(() -> Application.launch(UiGameWindow.class)).start();
        UiGameWindow.createShipsMode();
        synchronized (UiGameWindow.ships){
            while (UiGameWindow.ships.size() != Game.SHIP_SIZES.size()){
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
    public String getName() {
        return "UiPlayer";
    }

    public void setGrid(GameGrid grid) {
        UiGameWindow.myGrid = grid;
        UiGameWindow.gameMode();
    }

    public void handleResult(boolean victory){
        UiGameWindow.showResult(victory);
    }

    public static class Monitor {
        public GridSquare nextShot;
    }

}
