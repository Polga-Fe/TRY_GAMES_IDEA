package com.rpgito;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        switchToOpeningScene();
    }

    public static void switchToOpeningScene() {
        loadScene("opening");
    }
    public static void switchToOptionsScene() {
        loadScene("options");
    }
    public static void switchToSelectionScene() {
        loadScene("selection");
    }
    public static void switchToGameScene() {
        loadScene("gamePage");
    }

    private static void loadScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();

            scene.focusOwnerProperty().addListener((obs, oldFocus, newFocus) -> {
                System.out.println("Scene focus owner changed: " + newFocus);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
