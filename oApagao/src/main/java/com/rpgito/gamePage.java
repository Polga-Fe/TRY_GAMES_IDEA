package com.rpgito;

import com.back.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class gamePage {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private static ProgressBar healthBar;

    @FXML
    public void initialize() {
        GameController.startGame(gameCanvas, healthBar);

        gameCanvas.setFocusTraversable(true);
        gameCanvas.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            handleKeyPress(event.getCode());
        });

        gameCanvas.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            handleKeyRelease(event.getCode());
        });
    }

    private void handleKeyPress(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            GameController.setAttack(true);
        } else {
            GameController.keyTecladoPress(keyCode);
        }
    }

    private void handleKeyRelease(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            GameController.setAttack(false);
        } else {
            GameController.keyTecladoSolto(keyCode);
        }
    }

    @FXML
    private void returnOpening(ActionEvent event) {
        App.switchToOpeningScene(); // Altera a cena para a página de abertura
    }
}
