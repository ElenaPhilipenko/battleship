package com.github.elkurilina.seabattle.player.uiplayer;

import com.github.elkurilina.seabattle.GameGrid;
import com.github.elkurilina.seabattle.GridSquare;
import com.github.elkurilina.seabattle.SquareState;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static com.github.elkurilina.seabattle.player.UiPlayer.IMAGES;
import static javafx.stage.Modality.WINDOW_MODAL;

/**
 * @author Elena Kurilina
 */
public class GameStage extends Stage {
    private Map<GridSquare, Button> myShots = new HashMap<>();
    private Map<GridSquare, Button> opponentShots = new HashMap<>();

    private final BlockingQueue<GridSquare> nextShot;

    public GameGrid ownGridValues;
    public GameGrid opponentGridValues;

    public GameStage(BlockingQueue<GridSquare> nextShot) {
        this.nextShot = nextShot;
    }

    public void gameMode() {
        new JFXPanel();
        Platform.runLater(() -> {
            HBox root = new HBox();
            createGameGrids(root);
            root.setSpacing(10);
            setScene(new Scene(root));
            setWidth(810);
            setHeight(340);
            setTitle("BattleShip");
            show();
        });
    }

    public void showResult(boolean victory) {
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

    public void updateGrids() {
        if (myShots.size() > 0 && opponentShots.size() > 0) {
            ownGridValues.squares.forEach(sq -> {
                final Button b = opponentShots.get(sq);
                final SquareState squareState = ownGridValues.getSquareState(sq);
                if (squareState != SquareState.HIDDEN) {
                    b.setOnAction(e -> {
                    });
                }
                b.setGraphic(new ImageView(IMAGES.get(squareState)));
            });
            opponentGridValues.squares.forEach(sq -> myShots.get(sq).setGraphic(
                    new ImageView(IMAGES.get(opponentGridValues.getSquareState(sq)))));
        }
    }

    private void createGameGrids(HBox root) {
        final GridPane gridPane = new GridPane();
        ownGridValues.squares.forEach(s -> {
            final Button b = new Button();
            b.setGraphic(new ImageView(IMAGES.get(ownGridValues.getSquareState(s))));
            b.setMinSize(40, 30);
            opponentShots.put(s, b);
            gridPane.add(b, s.y, s.x);
        });
        root.getChildren().add(gridPane);

        final GridPane opponentGridPane = new GridPane();
        ownGridValues.squares.forEach(s -> {
            final Button b = new Button();
            b.setGraphic(new ImageView(IMAGES.get(SquareState.EMPTY)));
            b.setMinSize(40, 30);
            myShots.put(s, b);
            b.setOnAction(e -> {
                final GridSquare candidate = new GridSquare(s.x, s.y);
                if (opponentGridValues.findNotShotSquares().contains(candidate) && nextShot.size() == 0) {
                    nextShot.add(candidate);
                }
            });
            opponentGridPane.add(b, s.y, s.x);
        });
        root.getChildren().add(opponentGridPane);
    }

}
