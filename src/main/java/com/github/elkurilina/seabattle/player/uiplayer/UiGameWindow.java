package com.github.elkurilina.seabattle.player.uiplayer;

import com.github.elkurilina.seabattle.*;
import com.github.elkurilina.seabattle.player.RandomShipLocator;
import com.github.elkurilina.seabattle.player.UiPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

import static javafx.stage.Modality.WINDOW_MODAL;

/**
 * @author Elena Kurilina
 */
public class UiGameWindow extends Application {
    private static final Map<GridSquare, Button> myShots = new HashMap<>();
    private static final Map<GridSquare, Button> myShips = new HashMap<>();

    public static GameGrid myGrid;
    public static GameGrid opponentGrid;


    public static final Map<GridSquare, Button> gridButtons = new HashMap<>();
    private static final Collection<Collection<GridSquare>> ships = new ArrayList<>();
    private static final Label currentTip = new Label("Locate your ships.");

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        UiGameWindow.stage = stage;
        stage.setOnCloseRequest(e -> System.exit(0));
    }

    public static void gameMode() {
        new JFXPanel();
        Platform.runLater(() -> {
            HBox root = new HBox();
            createGameGrids(root);
            root.setSpacing(10);
            stage.setScene(new Scene(root));
            stage.setWidth(590);
            stage.setHeight(300);
            stage.setTitle("BattleShip");
            stage.show();
        });
    }

    public static void createShipsMode() {
        new JFXPanel();
        Platform.runLater(() -> {
            final GridPane gridPane = new GridPane();
            final Collection<Integer> shipSizes = new ArrayList<>(Game.SHIP_SIZES);
            currentTip.setText("Create ship with " + Collections.max(shipSizes) + " squares");
            createEmptyGridForCreatingShips(gridPane, shipSizes);
            final VBox root = new VBox();
            final Button randomButton = new Button("Generate random ships");
            randomButton.setOnAction(e -> {
                try {
                    UiPlayer.ships.put(new RandomShipLocator().createShips(Game.SHIP_SIZES, Game.GRID_SIZE));
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

            });
            root.getChildren().add(randomButton);
            root.setSpacing(5);
            root.getChildren().add(currentTip);
            root.getChildren().add(gridPane);
            stage.setScene(new Scene(root));
            stage.setWidth(300);
            stage.setHeight(350);
            stage.setTitle("Creating your ships");
            stage.show();
        });
    }

    public static void showResult(boolean victory) {
        new JFXPanel();
        Platform.runLater(() -> {
            final Stage congrats = new Stage();
            congrats.initModality(WINDOW_MODAL);
            congrats.centerOnScreen();
            congrats.setWidth(250);
            congrats.setHeight(100);
            final VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.getChildren().add(new Label(victory ? "Congratulations! You have won!" : "Sorry, You have lost."));
            congrats.setScene(new Scene(vBox));
            congrats.show();
        });
    }

    private static void createGameGrids(HBox root) {
        final GridPane gridPane = new GridPane();
        myGrid.squares.forEach(s -> {
            final Button b = new Button(myGrid.getSquareState(s).toString());
            myShips.put(s, b);
            gridPane.add(b, s.y, s.x);
        });
        root.getChildren().add(gridPane);
        final GridPane opponentGridPane = new GridPane();
        myGrid.squares.forEach(s -> {
            final Button b = new Button(SquareState.HIDDEN.toString());
            myShots.put(s, b);
            b.setOnAction(e -> {
                if (b.getText().equals(SquareState.HIDDEN.toString())) {
                    try {
                        UiPlayer.nextShot.put(new GridSquare(s.x, s.y));
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            });
            opponentGridPane.add(b, s.y, s.x);
        });
        root.getChildren().add(opponentGridPane);
    }

    private static void createEmptyGridForCreatingShips(GridPane gridPane, Collection<Integer> shipSizes) {
        final Collection<GridSquare> ship = new ArrayList<>();
        for (int i = 0; i < Game.GRID_SIZE; i++) {
            for (int j = 0; j < Game.GRID_SIZE; j++) {
                final Button b = new Button(SquareState.EMPTY.toString());
                final GridSquare square = new GridSquare(i, j);
                b.setOnAction(e -> {
                    if (onEmptyButtonClick(shipSizes, ship, b, square)) {
                        stage.close();
                    }
                });
                gridButtons.put(square, b);
                gridPane.add(b, j, i);
            }
        }
    }

    public static void updateGrids() {
        new JFXPanel();
        Platform.runLater(() -> {
            if (myShots.size() > 0 && myShips.size() > 0) {
                myGrid.squares.forEach(sq -> {
                    final Button b = myShips.get(sq);
                    final SquareState squareState = myGrid.getSquareState(sq);
                    if (squareState != SquareState.HIDDEN) {
                        b.setOnAction(e -> {
                        });
                    }
                    b.setText(squareState.toString());
                });
                opponentGrid.squares.forEach(sq -> myShots.get(sq).setText(opponentGrid.getSquareState(sq).toString()));
            }
        });

    }

    private static boolean onEmptyButtonClick(Collection<Integer> shipSizes, Collection<GridSquare> ship, Button b, GridSquare square) {
        b.setText(SquareState.SHIP.toString());
        b.setOnAction(e -> onShipButtonClick(shipSizes, b, ship, square));
        return validatedAndAddSquare(shipSizes, ship, square);
    }

    private static void onShipButtonClick(Collection<Integer> shipSizes, Button b, Collection<GridSquare> ship, GridSquare square) {
        b.setText(SquareState.EMPTY.toString());
        b.setOnAction(e -> onEmptyButtonClick(shipSizes, ship, b, square));
        ship.remove(square);
        if (ship.isEmpty() || ShipsValidator.isShipValid(ship)) {
            currentTip.setText("Create ship with " + Collections.max(shipSizes) + " squares");
        }
    }

    private static boolean validatedAndAddSquare(Collection<Integer> shipSizes, Collection<GridSquare> ship, GridSquare square) {
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

    private static boolean addValidShip(Collection<Integer> shipSizes, Collection<GridSquare> ship) {
        shipSizes.remove(ship.size());
        if (shipSizes.isEmpty()) {
            try {
                UiPlayer.ships.put(ships);
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
