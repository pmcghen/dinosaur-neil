package com.example.dinosaurneil;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FeedReaderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 1024, 768);
        stage.setTitle("Dinosaur Neil RSS Reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}