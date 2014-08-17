package com.github.elkurilina.seabattle.player.uiplayer;

import com.github.elkurilina.seabattle.Game;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.ShipsValidator;
import com.github.elkurilina.seabattle.SquareState;
import com.github.elkurilina.seabattle.player.RandomShipLocator;
import com.github.elkurilina.seabattle.player.UiPlayer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

/**
 * @author Elena Kurilina
 */
public class ShipsCreatingStage extends Stage {
    private final BlockingQueue<Collection<Collection<GridSquare>>> shipsQueue;
    private final Collection<Collection<GridSquare>> ships = new ArrayList<>();
    private Label currentTip;

    public ShipsCreatingStage(BlockingQueue<Collection<Collection<GridSquare>>> shipsQueue) {
        this.shipsQueue = shipsQueue;
    }

    public void createShipsMode() {
        new JFXPanel();
        Platform.runLater(() -> {
            final GridPane gridPane = new GridPane();
            final Collection<Integer> shipSizes = new ArrayList<>(Game.SHIP_SIZES);
            currentTip = new Label();
            currentTip.setText("Create ship with " + Collections.max(shipSizes) + " squares");
            createEmptyGridForCreatingShips(gridPane, shipSizes);
            final VBox root = new VBox();
            final Button randomButton = new Button("Generate random ships");
            randomButton.setOnAction(e -> {
                try {
                    shipsQueue.put(new RandomShipLocator().createShips(Game.SHIP_SIZES, Game.GRID_SIZE));
                    close();
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

            });
            root.getChildren().add(randomButton);
            root.setSpacing(5);
            root.getChildren().add(currentTip);
            root.getChildren().add(gridPane);
            setScene(new Scene(root));
            setWidth(400);
            setHeight(400);
            setTitle("Creating your ships");
            show();
        });
    }


    private void createEmptyGridForCreatingShips(GridPane gridPane, Collection<Integer> shipSizes) {
        final Collection<GridSquare> ship = new ArrayList<>();
        for (int i = 0; i < Game.GRID_SIZE; i++) {
            for (int j = 0; j < Game.GRID_SIZE; j++) {
                final Button b = new Button();
                b.setGraphic(new ImageView(UiPlayer.IMAGES.get(SquareState.EMPTY)));
                b.setMinSize(40, 30);
                final GridSquare square = new GridSquare(i, j);
                b.setOnAction(e -> {
                    if (onEmptyButtonClick(shipSizes, ship, b, square)) {
                        close();
                    }
                });
                gridPane.add(b, j, i);
            }
        }
    }


    private boolean onEmptyButtonClick(Collection<Integer> shipSizes, Collection<GridSquare> ship, Button b, GridSquare square) {
        b.setGraphic(new ImageView(UiPlayer.IMAGES.get(SquareState.SHIP)));
        b.setOnAction(e -> onShipButtonClick(shipSizes, b, ship, square));
        return validatedAndAddSquare(shipSizes, ship, square);
    }

    private void onShipButtonClick(Collection<Integer> shipSizes, Button b, Collection<GridSquare> ship, GridSquare square) {
        b.setGraphic(new ImageView(UiPlayer.IMAGES.get(SquareState.EMPTY)));
        b.setOnAction(e -> onEmptyButtonClick(shipSizes, ship, b, square));
        ship.remove(square);
        if (ship.isEmpty() || ShipsValidator.isShipValid(ship)) {
            currentTip.setText("Create ship with " + Collections.max(shipSizes) + " squares");
        }
    }

    private boolean validatedAndAddSquare(Collection<Integer> shipSizes, Collection<GridSquare> ship, GridSquare square) {
        ship.add(square);
        final int max = Collections.max(shipSizes);
        if (!ShipsValidator.isShipValid(ship) || ship.size() > max) {
            currentTip.setText("Ship is not valid.");
        } else if (ship.size() == max) {
            final Collection<GridSquare> candidate = new ArrayList<>(ship);
            ships.add(candidate);
            if (ShipsValidator.isDistanceBetweenShipsValid(ships)) {
                if (addValidShip(shipSizes, ship)) return true;
            } else {
                currentTip.setText("Distance between ships is not valid.");
                ships.remove(candidate);
            }
        }
        return false;
    }

    private boolean addValidShip(Collection<Integer> shipSizes, Collection<GridSquare> ship) {
        shipSizes.remove(ship.size());
        if (shipSizes.isEmpty()) {
            try {
                shipsQueue.put(ships);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        currentTip.setText("Create ship with " + Collections.max(shipSizes) + " squares");
        ship.clear();
        return false;
    }

}
