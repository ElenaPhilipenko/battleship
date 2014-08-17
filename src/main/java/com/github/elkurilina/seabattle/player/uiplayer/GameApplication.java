package com.github.elkurilina.seabattle.player.uiplayer;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Elena Kurilina
 */
public class GameApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }
}
